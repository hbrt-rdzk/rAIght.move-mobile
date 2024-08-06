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
    val startDestination =
        if (authenticationViewModel.hasUser) HOME_ROUTE else AUTHENTICATION_ROUTE

    NavHost(navController, startDestination = startDestination) {
        composable(AUTHENTICATION_ROUTE) {
            AuthenticationScreen(navController)
        }
        composable(SIGN_UP_ROUTE) {
            RegisterScreen(navController, authenticationViewModel)
        }
        composable(SIGN_IN_BY_EMAIL_ROUTE) {
            EmailLoginScreen(navController, authenticationViewModel)
        }
        composable(SIGN_IN_BY_GOOGLE_ROUTE) {
            GoogleLoginScreen(navController, authenticationViewModel, userInfoViewModel)
        }
        composable(USER_CREATION_ROUTE) {
            UserInfoScreen(navController, userInfoViewModel, authenticationViewModel)
        }

        composable(CAMERA_ROUTE) {
            CameraScreen(navController, analysisViewModel, cameraViewModel)
        }
        composable(HOME_ROUTE) {
            HomeScreen(navController)
        }
        composable(CALENDAR_ROUTE) {
            CalendarScreen(navController)
        }
        composable(PROFILE_ROUTE) {
            ProfileScreen(navController, authenticationViewModel)
        }
    }
}
