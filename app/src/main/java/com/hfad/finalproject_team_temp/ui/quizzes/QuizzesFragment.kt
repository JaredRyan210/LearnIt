package com.hfad.finalproject_team_temp.ui.quizzes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.finalproject_team_temp.R
import com.hfad.finalproject_team_temp.databinding.FragmentQuizzesBinding

class QuizzesFragment : Fragment() {
    private var _binding: FragmentQuizzesBinding? = null
 //   var quiz_select = arrayListOf<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(QuizzesViewModel::class.java)

        _binding = FragmentQuizzesBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textGallery // getting unresolved reference here
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val db = FirebaseFirestore.getInstance()
        val spinner: Spinner = root.findViewById(R.id.QuizSelect)

        db.collection("quizzes")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val quizTitles = ArrayList<String>()
                    for (document in task.result!!) {
                        val title = document.getString("title")
                        title?.let { quizTitles.add(it) }
                    }
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        quizTitles
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                    spinner.adapter = adapter
                } else {
                    Log.w("QuizFragment", "Error getting quizzes", task.exception)
                }
            }



        val bToQuiz = root.findViewById<Button>(R.id.bToQuiz)
        bToQuiz.setOnClickListener {
            val selectedQuizTitle = spinner.selectedItem?.toString()
            Log.d(selectedQuizTitle, "selectedquiztitletest")
            val intent = Intent(activity, QuizDetail::class.java)
            intent.putExtra("ID", selectedQuizTitle)
            startActivity(intent)
        }

//
        val createQuiz = root.findViewById<Button>(R.id.createQuizButton)
        createQuiz.setOnClickListener {
            val intent = Intent(activity, CreateQuiz::class.java)
            startActivity(intent)
        }
//        val fragment = CreateQuizFragment()
//        val createQuiz = root.findViewById<Button>(R.id.createQuizButton)
//        createQuiz.setOnClickListener {
//            childFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_create_quiz, fragment).commit()
//        //findNavController().navigate(R.id.nav_host_fragment_content_create_quiz)
//        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}