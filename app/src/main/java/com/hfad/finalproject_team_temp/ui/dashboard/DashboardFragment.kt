package com.hfad.finalproject_team_temp.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.hfad.finalproject_team_temp.R
import com.hfad.finalproject_team_temp.databinding.FragmentDashboardBinding
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    private val textViews = mutableListOf<TextView>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val root: View = binding.root
        textViews.addAll(listOf(
            root.findViewById(R.id.textView),
            root.findViewById(R.id.textView7),
            root.findViewById(R.id.textView8),
            root.findViewById(R.id.textView9),
            root.findViewById(R.id.textView10)
        ))

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val savedUsername = dashboardViewModel.loadUsername(requireContext()) // obtain username of currently logged in user

        val username = arguments?.getString("username")
        if (dashboardViewModel.welcomeMessage.value.isNullOrEmpty()) {
            username?.let {
                dashboardViewModel.setWelcomeMessage(it, requireContext())
            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = binding.textGallery
        dashboardViewModel.welcomeMessage.observe(viewLifecycleOwner) { message ->
            textView.text = message
        }
        val savedUsername = dashboardViewModel.loadUsername(requireContext()) // obtain username of currently logged in user

        val username = arguments?.getString("username")

        if (dashboardViewModel.welcomeMessage.value.isNullOrEmpty()) {
            username?.let {
                dashboardViewModel.setWelcomeMessage(it, requireContext())
            }
        }

        val db = FirebaseFirestore.getInstance()
        val scoresCollection = db.collection("quizScores")
        scoresCollection
            .whereEqualTo("username", savedUsername)
            .orderBy("Score", Query.Direction.DESCENDING)
            .limit(5)
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.documents.forEachIndexed { index, document ->
                    val quizId = document.getString("quizID")
                    val score = document.getDouble("Score")
                    val formattedScore = String.format("%.2f%%", score?.times(100) ?: 1)

                    textViews.getOrNull(index)?.text = "$quizId     Score: $formattedScore"
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreError", "Error fetching data: $exception")
                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
            }
}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}