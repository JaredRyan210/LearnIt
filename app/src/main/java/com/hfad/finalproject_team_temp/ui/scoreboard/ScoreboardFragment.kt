package com.hfad.finalproject_team_temp.ui.scoreboard
import com.google.firebase.firestore.Query

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.finalproject_team_temp.R

class ScoreboardFragment : Fragment() {

    private var scoreboardList = mutableListOf<Pair<String, Double>>()
    private var titlesList = mutableListOf<String>()
    private var descriptionList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scoreboard, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ScoreboardAdapter(titlesList, descriptionList, imagesList)
        fetchScores()
        return view
    }

    private fun fetchScores() {
        val db = FirebaseFirestore.getInstance()
        db.collection("quizScores")
            .orderBy("Score", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val userScores = mutableMapOf<String, MutableList<Double>>()

                for (document in documents) {
                    val username = document.getString("username")
                    val score = document.getDouble("Score") ?: 0.0

                    if (username != null) {
                        if (!userScores.containsKey(username)) {
                            userScores[username] = mutableListOf(score)
                        } else {
                            userScores[username]?.add(score)
                        }
                    }
                }

                val userAverages = mutableListOf<Pair<String, Double>>()

                for ((username, scores) in userScores) {
                    val averageScore = scores.average()
                    userAverages.add(username to averageScore)
                }

                userAverages.sortByDescending { it.second }

                scoreboardList.clear()
                scoreboardList.addAll(userAverages)

                titlesList.clear()
                descriptionList.clear()
                imagesList.clear()

                for ((username, averageScore) in scoreboardList) {
                    titlesList.add(username)
                    descriptionList.add("Average Score: ${String.format("%.2f%%", averageScore * 100)}")
                    imagesList.add(R.mipmap.ic_launcher_round)
                }

                val recyclerView: RecyclerView? = view?.findViewById(R.id.rv_recyclerView)
                recyclerView?.adapter?.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w("ScoreboardFragment", "Error displaying scoreboard", e)
            }
    }
}

