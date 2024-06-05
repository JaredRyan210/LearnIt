package com.hfad.finalproject_team_temp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hfad.finalproject_team_temp.R
import com.hfad.finalproject_team_temp.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val signUpButton: Button = view.findViewById(R.id.bSignup)
        signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_signUpFragment)
        }

        val loginButton: Button = view.findViewById(R.id.bLogin)
        loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_loginFragment)
        }

        //val textView: TextView = binding.textHome
        //homeViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        //}
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}