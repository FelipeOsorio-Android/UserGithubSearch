package devandroid.felipe.usergithubsearch.fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import devandroid.felipe.usergithubsearch.R
import devandroid.felipe.usergithubsearch.databinding.FragmentSplashscreenBinding

class SplashScreenFragment : Fragment(), AnimatorListener {

    private var _binding: FragmentSplashscreenBinding? = null
    private val binding get() = _binding!!
    private val animationGithub by lazy { binding.fragmentSplashscreenAnimationGithub }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashscreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animationGithub.playAnimation()
        animationGithub.addAnimatorListener(this)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAnimationStart(animator: Animator) {
    }

    override fun onAnimationEnd(animator: Animator) {
        if (animator.isRunning) {
            animator.end()
            onAnimationEnd(animator)
        } else {
            findNavController().navigate(R.id.go_to_userSearchFragment)
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        }
    }

    override fun onAnimationCancel(animator: Animator) {
    }

    override fun onAnimationRepeat(animator: Animator) {
    }


}