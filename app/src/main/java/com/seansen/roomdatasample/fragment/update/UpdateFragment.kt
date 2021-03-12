package com.seansen.roomdatasample.fragment.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.seansen.roomdatasample.R
import com.seansen.roomdatasample.fragment.update.UpdateFragmentArgs
import com.seansen.roomdatasample.model.User
import com.seansen.roomdatasample.viewmodel.UserViewModel

class UpdateFragment: Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var etFirstname: EditText
    private lateinit var etLastname: EditText
    private lateinit var etAge: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        etFirstname = view.findViewById<EditText>(R.id.updateFirstName_et)
        etLastname = view.findViewById<EditText>(R.id.updateLastName_et)
        etAge = view.findViewById<EditText>(R.id.updateAge_et)

        etFirstname.setText(args.currentUser.firstname)
        etLastname.setText(args.currentUser.lastname)
        etAge.setText(args.currentUser.age.toString())

        view.findViewById<Button>(R.id.update_btn).setOnClickListener {
            updateItem()
        }

        // Add menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val firstName = etFirstname.text.toString()
        val lastName = etLastname.text.toString()
        val age = Integer.parseInt(etAge.text.toString())

        if (inputCheck(firstName, lastName, etAge.text)) {
            // Create User Object
            val updatedUser = User(args.currentUser.id, firstName, lastName, age)
            // Update Current User
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_FirstFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentUser.firstname}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_FirstFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentUser.firstname}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstname}?")
        builder.create().show()
    }
}