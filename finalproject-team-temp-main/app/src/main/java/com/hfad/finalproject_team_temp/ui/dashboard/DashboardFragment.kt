package com.hfad.finalproject_team_temp.ui.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hfad.finalproject_team_temp.R
import com.hfad.finalproject_team_temp.databinding.FragmentDashboardBinding
import com.hfad.finalproject_team_temp.databinding.FragmentQuizzesBinding
import com.hfad.finalproject_team_temp.ui.quizzes.QuizzesViewModel
import androidx.fragment.app.activityViewModels

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val galleryViewModel =
            //ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
    /*
        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

     */

        val textView: TextView = binding.textGallery

        dashboardViewModel.welcomeMessage.observe(viewLifecycleOwner) { message ->
            textView.text = message
        }

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = arguments?.getString("username")
        if (dashboardViewModel.welcomeMessage.value.isNullOrEmpty()) {
            username?.let { dashboardViewModel.setWelcomeMessage(it)}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}