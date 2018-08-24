package rahul.com.movies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkConnectionChecker {


	public static boolean isNetworkAvailable(Context oContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) oContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// Get current active network
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();

		return activeNetworkInfo != null && activeNetworkInfo.isConnected();

	}

}
