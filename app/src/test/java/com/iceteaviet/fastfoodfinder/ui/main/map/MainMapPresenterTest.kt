package com.iceteaviet.fastfoodfinder.ui.main.map

import android.os.Build
import com.google.android.gms.maps.model.LatLng
import com.iceteaviet.fastfoodfinder.data.DataManager
import com.iceteaviet.fastfoodfinder.data.remote.routing.model.MapsDirection
import com.iceteaviet.fastfoodfinder.data.remote.store.model.Store
import com.iceteaviet.fastfoodfinder.location.GoogleLocationManager
import com.iceteaviet.fastfoodfinder.location.LatLngAlt
import com.iceteaviet.fastfoodfinder.location.LocationListener
import com.iceteaviet.fastfoodfinder.utils.StoreType
import com.iceteaviet.fastfoodfinder.utils.exception.NotFoundException
import com.iceteaviet.fastfoodfinder.utils.exception.UnknownException
import com.iceteaviet.fastfoodfinder.utils.getFakeMapsDirection
import com.iceteaviet.fastfoodfinder.utils.getFakeStoreList
import com.iceteaviet.fastfoodfinder.utils.rx.SchedulerProvider
import com.iceteaviet.fastfoodfinder.utils.rx.TrampolineSchedulerProvider
import com.iceteaviet.fastfoodfinder.utils.setFinalStatic
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.eq
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*

/**
 * Created by tom on 2019-06-15.
 */
class MainMapPresenterTest {
    @Mock
    private lateinit var mainMapView: MainMapContract.View

    @Mock
    private lateinit var dataManager: DataManager

    @Mock
    private lateinit var locationManager: GoogleLocationManager

    @Captor
    private lateinit var locationCallbackCaptor: ArgumentCaptor<LocationListener>

    private lateinit var mainMapPresenter: MainMapPresenter

    private lateinit var schedulerProvider: SchedulerProvider

    @Before
    fun setupPresenter() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = TrampolineSchedulerProvider()

        mainMapPresenter = MainMapPresenter(dataManager, schedulerProvider, locationManager, mainMapView)
    }

    @After
    fun tearDown() {
        setFinalStatic(Build.VERSION::class.java.getField("SDK_INT"), 0)
    }

    @Test
    fun subscribeTest_locationPermissionGranted() {
        // Preconditions
        Mockito.`when`(mainMapView.isLocationPermissionGranted()).thenReturn(true)

        // Mocks
        `when`(dataManager.getAllStores()).thenReturn(Single.never())

        mainMapPresenter.subscribe()

        verify(mainMapView).setupMap()
        verify(mainMapView, never()).requestLocationPermission()
        verify(locationManager).requestLocationUpdates()
        verify(locationManager).subscribeLocationUpdate(mainMapPresenter)
    }

    @Test
    fun subscribeTest_devicePreLolipop() {
        // Preconditions
        setFinalStatic(Build.VERSION::class.java.getField("SDK_INT"), 18)

        // Mocks
        `when`(dataManager.getAllStores()).thenReturn(Single.never())

        mainMapPresenter.subscribe()

        verify(mainMapView).setupMap()
        verify(mainMapView, never()).requestLocationPermission()
        verify(locationManager).requestLocationUpdates()
        verify(locationManager).subscribeLocationUpdate(mainMapPresenter)
    }

    @Test
    fun subscribeTest_locationPermissionNotGranted() {
        // Preconditions
        setFinalStatic(Build.VERSION::class.java.getField("SDK_INT"), 24)
        Mockito.`when`(mainMapView.isLocationPermissionGranted()).thenReturn(false)

        // Mocks
        `when`(dataManager.getAllStores()).thenReturn(Single.never())

        mainMapPresenter.subscribe()

        verify(mainMapView).setupMap()
        verify(mainMapView).requestLocationPermission()
        verify(locationManager, never()).requestLocationUpdates()
        verify(locationManager, never()).subscribeLocationUpdate(mainMapPresenter)
    }

    @Test
    fun onGetMapAsyncTest_emptyStoreList() {
        // Preconditions
        mainMapPresenter.storeList = ArrayList()

        mainMapPresenter.onGetMapAsync()

        verify(mainMapView, never()).addMarkersToMap(ArgumentMatchers.anyList())
    }

    @Test
    fun onGetMapAsyncTest_haveStoreList() {
        // Preconditions
        mainMapPresenter.storeList = stores

        mainMapPresenter.onGetMapAsync()

        verify(mainMapView).addMarkersToMap(stores)
    }

    @Test
    fun onGetMapAsyncTest_locationPermissionNotGranted() {
        // Preconditions
        mainMapPresenter.locationGranted = false

        mainMapPresenter.onGetMapAsync()

        verify(mainMapView, never()).setMyLocationEnabled(true)
    }

    @Test
    fun onGetMapAsyncTest_locationPermissionGranted_notHaveLastLocation() {
        // Preconditions
        mainMapPresenter.locationGranted = true

        mainMapPresenter.onGetMapAsync()

        verify(mainMapView, atLeastOnce()).setMyLocationEnabled(true)
        verify(mainMapView).setupMapEventHandlers()
        verify(mainMapView).showCannotGetLocationMessage()
    }

    @Test
    fun onGetMapAsyncTest_locationPermissionGranted_haveLastLocation() {
        // Preconditions
        `when`(locationManager.getCurrentLocation()).thenReturn(location)
        val spyMainMapPresenter = spy(mainMapPresenter)
        spyMainMapPresenter.locationGranted = true

        spyMainMapPresenter.onGetMapAsync()

        verify(mainMapView, atLeastOnce()).setMyLocationEnabled(true)
        verify(spyMainMapPresenter, atLeastOnce()).requestCurrentLocation()
        verify(mainMapView).setupMapEventHandlers()
        assertThat(spyMainMapPresenter.currLocation).isNotNull()
        assertThat(spyMainMapPresenter.currLocation).isEqualTo(LatLng(location.latitude, location.longitude))
        verify(spyMainMapPresenter).subscribeMapCameraPositionChange()
        verify(spyMainMapPresenter).subscribeNewVisibleStore()
    }

    @Test
    fun requestCurrentLocationTest_haveLastLocation_notZoomToUser() {
        // Preconditions
        `when`(locationManager.getCurrentLocation()).thenReturn(location)
        mainMapPresenter.isZoomToUser = false

        mainMapPresenter.requestCurrentLocation()

        assertThat(mainMapPresenter.currLocation).isNotNull()
        assertThat(mainMapPresenter.currLocation).isEqualTo(LatLng(location.latitude, location.longitude))
        verify(mainMapView).animateMapCamera(LatLng(location.latitude, location.longitude), false)
    }

    @Test
    fun requestCurrentLocationTest_haveLastLocation_zoomToUser() {
        // Preconditions
        `when`(locationManager.getCurrentLocation()).thenReturn(location)
        mainMapPresenter.isZoomToUser = true

        mainMapPresenter.requestCurrentLocation()

        assertThat(mainMapPresenter.currLocation).isNotNull()
        assertThat(mainMapPresenter.currLocation).isEqualTo(LatLng(location.latitude, location.longitude))
        verify(mainMapView, never()).animateMapCamera(anyObject(), ArgumentMatchers.anyBoolean())
    }

    @Test
    fun requestCurrentLocationTest_notHaveLastLocation() {
        mainMapPresenter.requestCurrentLocation()

        verify(mainMapView).showCannotGetLocationMessage()
    }

    @Test
    fun onLocationPermissionGrantedTest() {
        // Preconditions
        mainMapPresenter.onLocationPermissionGranted()

        verify(locationManager).getCurrentLocation()
        verify(locationManager).requestLocationUpdates()
        verify(locationManager).subscribeLocationUpdate(capture(locationCallbackCaptor))
        verify(mainMapView).setMyLocationEnabled(true)

        locationCallbackCaptor.value.onLocationChanged(location)

        assertThat(mainMapPresenter.currLocation).isNotNull()
        assertThat(mainMapPresenter.currLocation).isEqualTo(LatLng(location.latitude, location.longitude))
    }

    @Test
    fun onLocationChangeTest_notZoomToUser() {
        // Preconditions
        mainMapPresenter.locationGranted = true
        mainMapPresenter.isZoomToUser = false

        mainMapPresenter.onLocationChanged(location)

        assertThat(mainMapPresenter.currLocation).isNotNull()
        assertThat(mainMapPresenter.currLocation).isEqualTo(LatLng(location.latitude, location.longitude))
        verify(mainMapView).animateMapCamera(LatLng(location.latitude, location.longitude), false)
    }

    @Test
    fun subscribeTest_getStoresLocalEmpty() {
        // Preconditions
        `when`(dataManager.getAllStores()).thenReturn(Single.just(ArrayList()))

        mainMapPresenter.subscribe()

        verify(mainMapView).setupMap()
        verify(mainMapView).showWarningMessage(ArgumentMatchers.anyInt())
        verify(mainMapView, never()).addMarkersToMap(ArgumentMatchers.anyList())
    }

    @Test
    fun subscribeTest_getStoresLocalError() {
        // Preconditions
        `when`(dataManager.getAllStores()).thenReturn(Single.error(UnknownException()))

        mainMapPresenter.subscribe()

        verify(mainMapView).setupMap()
        verify(mainMapView).showWarningMessage(ArgumentMatchers.anyInt())
        verify(mainMapView, never()).addMarkersToMap(ArgumentMatchers.anyList())
    }

    @Test
    fun subscribeTest_getStoresLocalSuccess() {
        // Preconditions
        `when`(dataManager.getAllStores()).thenReturn(Single.just(stores))

        mainMapPresenter.subscribe()

        verify(mainMapView).setupMap()
        verify(mainMapView, never()).showWarningMessage(ArgumentMatchers.anyInt())
        verify(mainMapView).addMarkersToMap(stores.toMutableList())
    }

    @Test
    fun onNavigationButtonClickTest_showNavigationScreen() {
        // Preconditions
        mainMapPresenter.currLocation = latLng

        // Mocks
        `when`(dataManager.getMapsDirection(ArgumentMatchers.anyMap(), eq(store))).thenReturn(Single.just(mapsDirection))

        mainMapPresenter.onNavigationButtonClick(store)

        verify(mainMapView).showMapRoutingView(store, mapsDirection)
    }

    @Test
    fun onNavigationButtonClickTest_validStoreLocation_generalError() {
        // Preconditions
        mainMapPresenter.currLocation = latLng
        val store = Store(STORE_ID, STORE_TITLE, STORE_ADDRESS, STORE_LAT, STORE_LNG, STORE_TEL, STORE_TYPE)

        // Mocks
        `when`(dataManager.getMapsDirection(ArgumentMatchers.anyMap(), eq(store))).thenReturn(Single.error(NotFoundException()))

        mainMapPresenter.onNavigationButtonClick(store)

        verify(mainMapView).showGeneralErrorMessage()
    }

    @Test
    fun onNavigationButtonClickTest_invalidStoreLocation() {
        // Preconditions
        val store = Store(STORE_ID, STORE_TITLE, STORE_ADDRESS, STORE_INVALID_LAT, STORE_INVALID_LNG, STORE_TEL, STORE_TYPE)

        mainMapPresenter.onNavigationButtonClick(store)

        verify(mainMapView).showInvalidStoreLocationWarning()
    }

    @Test
    fun onNavigationButtonClickTest_nullCurrentLocation() {
        // Preconditions
        mainMapPresenter.currLocation = null

        val store = Store(STORE_ID, STORE_TITLE, STORE_ADDRESS, STORE_LAT, STORE_LNG, STORE_TEL, STORE_TYPE)

        mainMapPresenter.onNavigationButtonClick(store)

        verify(mainMapView).showCannotGetLocationMessage()
    }

    @Test
    fun onClearOldMapDataTest() {
        mainMapPresenter.onClearOldMapData()

        assertThat(mainMapPresenter.markerSparseArray.size()).isZero()
        verify(mainMapView).clearMapData()
    }

    // Workaround solution
    private fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

    companion object {
        private const val STORE_ID = 123
        private const val STORE_TITLE = "store_title"
        private const val STORE_ADDRESS = "store_address"
        private const val STORE_LAT = "10.773996"
        private const val STORE_LNG = "106.6898035"
        private const val STORE_TEL = "012345678965"
        private const val STORE_TYPE = StoreType.TYPE_CIRCLE_K

        private const val STORE_INVALID_LAT = "0"
        private const val STORE_INVALID_LNG = "0"

        private val location = LatLngAlt(10.1234, 106.1234, 1.0)
        private val latLng = LatLng(10.1234, 106.1234)

        private val stores = getFakeStoreList()

        val store = Store(STORE_ID, STORE_TITLE, STORE_ADDRESS, STORE_LAT, STORE_LNG, STORE_TEL, STORE_TYPE)

        private val invalidMapsDirection = MapsDirection()
        private val mapsDirection = getFakeMapsDirection()
    }
}