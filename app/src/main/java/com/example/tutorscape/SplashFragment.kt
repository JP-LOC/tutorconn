package com.example.tutorscape
/*
Edit 9/21/2020
 */
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.navigation.fragment.findNavController
import com.google.common.reflect.Reflection.getPackageName

class SplashFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

/*            Uri uri = Uri.parse(URL) //Declare your url here.

    VideoView mVideoView  = (VideoView)findViewById(R.id.videoview)
    mVideoView.setMediaController(new MediaController(this))
    mVideoView.setVideoURI(uri)
    mVideoView.requestFocus()
    mVideoView.start()
 */
    ): View? {
        Handler().postDelayed({
            if(onBoardingFinished()){
                findNavController().navigate(R.id.action_splashFragment_to_home1)
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }, 4500)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

}