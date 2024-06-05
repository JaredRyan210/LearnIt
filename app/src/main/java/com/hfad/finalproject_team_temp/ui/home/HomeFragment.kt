package com.hfad.finalproject_team_temp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hfad.finalproject_team_temp.R
import com.hfad.finalproject_team_temp.SessionManager
import com.hfad.finalproject_team_temp.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {
    private lateinit var sessionManager: SessionManager

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        sessionManager = SessionManager(requireContext())

        val signUpButton: Button = view.findViewById(R.id.bSignup)
        signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_signUpFragment)
        }

        val loginButton: Button = view.findViewById(R.id.bLogin)
        loginButton.setOnClickListener {
            if (sessionManager.isLoggedIn()) {

            } else {
                findNavController().navigate(R.id.action_nav_home_to_loginFragment)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}