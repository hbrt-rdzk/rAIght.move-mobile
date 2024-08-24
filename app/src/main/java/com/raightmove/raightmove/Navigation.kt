import Destinations.AUTHENTICATION_ROUTE
import Destinations.CALENDAR_ROUTE
import Destinations.CAMERA_ROUTE
import Destinations.HOME_ROUTE
import Destinations.PROFILE_ROUTE
import Destinations.SIGN_IN_BY_EMAIL_ROUTE
import Destinations.SIGN_IN_BY_GOOGLE_ROUTE
import Destinations.SIGN_UP_ROUTE
import Destinations.USER_CREATION_ROUTE
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raightmove.raightmove.ui.screens.authentication.AuthenticationScreen
import com.raightmove.raightmove.ui.screens.authentication.EmailLoginScreen
import com.raightmove.raightmove.ui.screens.authentication.GoogleLoginScreen
import com.raightmove.raightmove.ui.screens.authentication.RegisterScreen
import com.raightmove.raightmove.ui.screens.authentication.UserInfoScreen
import com.raightmove.raightmove.ui.screens.main.CalendarScreen
import com.raightmove.raightmove.ui.screens.main.CameraScreen
import com.raightmove.raightmove.ui.screens.main.HomeScreen
import com.raightmove.raightmove.ui.screens.main.ProfileScreen
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel
import com.raightmove.raightmove.viewmodels.CameraViewModel
import com.raightmove.raightmove.viewmodels.ExerciseAnalysisViewModel
import com.raightmove.raightmove.viewmodels.UserInfoViewModel

object Destinations {
    const val HOME_ROUTE = "home"
    const val AUTHENTICATION_ROUTE = "authentication"
    const val SIGN_UP_ROUTE = "register"
    const val SIGN_IN_BY_EMAIL_ROUTE = "email_login"
    const val SIGN_IN_BY_GOOGLE_ROUTE = "google_login"
    const val CAMERA_ROUTE = "camera"
    const val CALENDAR_ROUTE = "calendar"
    const val PROFILE_ROUTE = "profile"
    const val USER_CREATION_ROUTE = "user_info"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authenticationViewModel = AuthenticationViewModel()
    val userInfoViewModel = UserInfoViewModel()
    val analysisViewModel = ExerciseAnalysisViewModel()
    val cameraViewModel = CameraViewModel()
    val startDestination = if (authenticationViewModel.hasUser) HOME_ROUTE else AUTHENTICATION_ROUTE

    NavHost(navController, startDestination = startDestination) {
        composable(AUTHENTICATION_ROUTE) {
            AuthenticationScreen(navController)
        }
        composable(SIGN_UP_ROUTE) {
            val loginUiState = authenticationViewModel.loginUIState
            val error by authenticationViewModel.error.collectAsState()
            val isSuccessLogin by authenticationViewModel.isSuccessLogin.collectAsState()
            val isLoading by authenticationViewModel.isLoading.collectAsState()
            RegisterScreen(
                navController = navController,
                loginUIState = loginUiState,
                error = error,
                isSuccessLogin = isSuccessLogin,
                isLoading = isLoading,
                onEnter = authenticationViewModel::resetState,
                createUser = authenticationViewModel::createUser,
                onUserNameChange = authenticationViewModel::onUserNameSignUp,
                onPasswordChange = authenticationViewModel::onPasswordSignUp,
                onPasswordConfirmationChange = authenticationViewModel::onConfirmPasswordSignUp
            )
        }
        composable(SIGN_IN_BY_EMAIL_ROUTE) {
            val loginUiState = authenticationViewModel.loginUIState
            val error by authenticationViewModel.error.collectAsState()
            val isSuccessLogin by authenticationViewModel.isSuccessLogin.collectAsState()
            val isLoading by authenticationViewModel.isLoading.collectAsState()

            EmailLoginScreen(
                navController = navController,
                loginUIState = loginUiState,
                error = error,
                isSuccessLogin = isSuccessLogin,
                isLoading = isLoading,
                onEnter = authenticationViewModel::resetState,
                loginUser = authenticationViewModel::loginUser,
                onUserNameChange = authenticationViewModel::onUserNameChange,
                onPasswordChange = authenticationViewModel::onPasswordChange,
            )
        }
        composable(SIGN_IN_BY_GOOGLE_ROUTE) {
            val userInfoUiState = userInfoViewModel.userInfoUiState
            val loginError by authenticationViewModel.error.collectAsState()
            val userCreationError by userInfoViewModel.error.collectAsState()
            val isSuccessLogin by authenticationViewModel.isSuccessLogin.collectAsState()

            GoogleLoginScreen(
                navController = navController,
                userInfo = userInfoUiState,
                loginError = loginError,
                userCreationError = userCreationError,
                isSuccessLogin = isSuccessLogin,
                onEnter = authenticationViewModel::loginUserByGoogle,
                getUserId = authenticationViewModel::getUserId,
                fetchUserInfo = userInfoViewModel::fetchUserInfo,
            )
        }
        composable(USER_CREATION_ROUTE) {
            val userInfoUIState = userInfoViewModel.userInfoUiState
            val error by userInfoViewModel.error.collectAsState()
            val isLoading by userInfoViewModel.isLoading.collectAsState()
            val userInfo by userInfoViewModel.userInfo.collectAsState()

            UserInfoScreen(
                navController = navController,
                userInfoUIState = userInfoUIState,
                error = error,
                isLoading = isLoading,
                userInfo = userInfo,
                getUserID = authenticationViewModel::getUserId,
                createUserInfo = userInfoViewModel::addUserInfo,
                onNickChange = userInfoViewModel::onNickChange,
                onSexChange = userInfoViewModel::onSexChange,
                onHeightChange = userInfoViewModel::onHeightChange,
                onAgeChange = userInfoViewModel::onAgeChange
            )
        }

        composable(CAMERA_ROUTE) {
            val exercise by analysisViewModel.exercise.collectAsState()
            val state by analysisViewModel.analysisState.collectAsState()
            val landmarks by analysisViewModel.currentLandmarks.observeAsState()
            val videoLandmarks = analysisViewModel.videoLandmarks
            val joints by analysisViewModel.joints.collectAsState()
            val feedbacks by analysisViewModel.feedbacks.collectAsState()

            CameraScreen(
                navController = navController,
                exercise = exercise,
                state = state,
                landmarks = landmarks,
                videoLandmarks = videoLandmarks,
                joints = joints,
                feedbacks = feedbacks,
                startCamera = cameraViewModel::startCamera,
                processImage = analysisViewModel::processImageProxy,
                stopCamera = cameraViewModel::stopCamera,
                setPreviewView = cameraViewModel::setPreviewView,
                fetchFeedback = analysisViewModel::fetchFeedback,
                setJoints = analysisViewModel::setJoints,
                setFeedback = analysisViewModel::setFeedback,
                setState = analysisViewModel::setState,
                setExercise = analysisViewModel::setExercise,
                getUserID = authenticationViewModel::getUserId,
                addTraining = userInfoViewModel::addTraining,
            )
        }
        composable(HOME_ROUTE) {
            HomeScreen(navController)
        }
        composable(CALENDAR_ROUTE) {
            val trainings by userInfoViewModel.userTrainings.collectAsState()

            CalendarScreen(
                navController = navController,
                trainings = trainings,
                getUserID = authenticationViewModel::getUserId,
                fetchTraining = userInfoViewModel::fetchTrainings
            )
        }
        composable(PROFILE_ROUTE) {
            val userInfo by userInfoViewModel.userInfo.collectAsState()

            ProfileScreen(
                navController = navController,
                userInfo = userInfo,
                logOut = authenticationViewModel::logOut
            )
        }
    }
}
