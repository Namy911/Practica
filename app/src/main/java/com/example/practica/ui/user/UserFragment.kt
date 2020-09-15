package com.example.practica.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.example.practica.R
import com.example.practica.data.entity.User
import com.example.practica.databinding.UserFragmentBinding
import com.example.practica.ui.main.RosterFragment
import com.example.practica.ui.main.RosterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.user_fragment.*

@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding: UserFragmentBinding
    private val viewModel: RosterViewModel by viewModels()

    private var userId: Long = -1

    companion object {
        private const val TAG = "UserFragment"
        const val USER_MODEL = "ui.user.model"

        fun newInstance(user: User?) = UserFragment().apply {
            arguments = bundleOf(USER_MODEL to user)
        }
    }

//    override fun onResume() {
//        super.onResume()
//        edt_name.requestFocus()
//        showSoftKeyboard()
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return UserFragmentBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.user = arguments?.getParcelable(USER_MODEL)

        if (binding.user == null){
            fb_action.setOnClickListener { multipleSave() }
            fb_save_user.setOnClickListener{ save() }
        }else{
            fb_action.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_delete_24, null ))
            fb_action.setOnClickListener{ dialogDelete() }
            fb_save_user.setOnClickListener{ update() }
        }
    }

    private fun save(redirect: Boolean = true){
        if (validator()){
            val user = User(edt_name.text.toString(), edt_age.text.toString().toInt())
            Log.d(TAG, "save: $user")
            viewModel.save(user)
            if (redirect){ redirectTo() }
            Toast.makeText(activity, getString(R.string.msg_user_added, viewModel.save(user).toString()), Toast.LENGTH_SHORT).show()
        }
    }
    private fun update(){
        if (validator()) {
            val user = binding.user!!.copy(
                name = edt_name.text.toString(),
                age = edt_age.text.toString().toInt()
            )
            viewModel.update(user)
            redirectTo()
            Toast.makeText(activity, getString(R.string.msg_user_updated, binding.edtName.text), Toast.LENGTH_SHORT).show()
        }
    }

    private fun dialogDelete() {
        val dialog = AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.dialog_title, binding.user!!.name))
            setMessage(getString(R.string.dialog_subtitle))
            setNegativeButton(getString(R.string.negative_btn)) { _, _ -> }
            setPositiveButton(getString(R.string.positive_btn)) { _, _ -> deleteUser() }
            show()
        }
    }

    private fun deleteUser(){
        val user = binding.user!!.copy()
        viewModel.delete(user)
        Toast.makeText(activity, getString(R.string.msg_user_deleted, user.name), Toast.LENGTH_SHORT).show()
        redirectTo()
    }

    private fun multipleSave(){
        save(false)
        if (validator()){
            binding.edtName.text.clear()
            binding.edtAge.text.clear()
        }
    }

    private fun validator(): Boolean{
        if (edt_age.text.trim().isEmpty()){ edt_age.error = getString(R.string.error_user_age) }
        if (edt_name.text.trim().isEmpty()){ edt_name.error = getString(R.string.error_user_name) }
//        Log.d(TAG, "validator: ${(binding.edtName.text.isNotEmpty() && binding.edtAge.text.isNotEmpty())}")
        return (binding.edtName.text.isNotEmpty() && binding.edtAge.text.isNotEmpty())
    }

    // Not working
    fun showSoftKeyboard(){
//        val imm = edt_name.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm?.showSoftInput(edt_name, InputMethodManager.SHOW_IMPLICIT)
//        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

    }

    private fun redirectTo(){
        activity?.supportFragmentManager?.commit { replace(R.id.container, RosterFragment.newInstance()) }
    }
}
