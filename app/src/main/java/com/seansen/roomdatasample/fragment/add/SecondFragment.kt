package com.seansen.roomdatasample.fragment.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.seansen.roomdatasample.R
import com.seansen.roomdatasample.model.User
import com.seansen.roomdatasample.viewmodel.UserViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_second, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        view.findViewById<Button>(R.id.buttonSave).setOnClickListener {
            insertDataToDatabase();
        }
    }

    private fun insertDataToDatabase() {
        val firstname = rootView.findViewById<EditText>(R.id.editTextFirstname).text.toString()
        val lastname = rootView.findViewById<EditText>(R.id.editTextLastname).text.toString()
        val age = rootView.findViewById<EditText>(R.id.editTextAge).text

        if(inputCheck(firstname, lastname, age)) {
            // Create User Object
            val user = User(0, firstname, lastname, Integer.parseInt(age.toString()))
            mUserViewModel.addUser(user)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstname: String, lastname: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstname) && TextUtils.isEmpty(lastname) && age.isEmpty())
    }
}