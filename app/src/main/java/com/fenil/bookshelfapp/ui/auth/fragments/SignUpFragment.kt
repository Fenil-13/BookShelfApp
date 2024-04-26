package com.fenil.bookshelfapp.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.fenil.bookshelfapp.data.remote.data.CountryInfo
import com.fenil.bookshelfapp.databinding.FragmentSignUpBinding
import com.fenil.bookshelfapp.domain.model.User
import com.fenil.bookshelfapp.ui.auth.AuthViewModel
import com.fenil.bookshelfapp.ui.auth.CountryViewModel
import com.fenil.bookshelfapp.DelayAwareClickListener.DelayAwareClickListener
import com.fenil.bookshelfapp.DelayAwareClickListener.Status
import com.fenil.bookshelfapp.DelayAwareClickListener.isEmpty
import com.fenil.bookshelfapp.DelayAwareClickListener.passwordPattern
import com.fenil.bookshelfapp.DelayAwareClickListener.showToast


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val countryViewModel: CountryViewModel by activityViewModels<CountryViewModel>()
    private val authViewModel: AuthViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observerViewModel()
    }

    private fun setupUi() {
        binding.btnLogin.setOnClickListener(DelayAwareClickListener{
            SignUpFragmentDirections.actionSignUpToLogin().let {
                findNavController().navigate(it)
            }
        })


        binding.btnSignUp.setOnClickListener(DelayAwareClickListener{
            if (binding.etName.isEmpty()){
                context?.showToast("Name cannot be empty")
                return@DelayAwareClickListener
            }else if (binding.etEmail.isEmpty()){
                context?.showToast("Email cannot be empty")
                return@DelayAwareClickListener
            }else if (binding.etPassword.isEmpty()){
                context?.showToast("Password cannot be empty")
                return@DelayAwareClickListener
            }else if (!passwordPattern.matches(binding.etPassword.text.toString())){
                context?.showToast("min 8 characters with atleast one number, special characters[!@#\$%&()],\n" +
                        "one lowercase letter, and one uppercase letter is mandatory.")
                return@DelayAwareClickListener
            }
            val selectedCountryCode = countryViewModel.getCountryCodeByName(
                binding.countrySpinner.selectedItem as String
            )
            val user = User(
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString(),
                country = selectedCountryCode
            )
            authViewModel.signUp(user)
        })
    }

    private fun observerViewModel() {
        countryViewModel.countryData.observe(viewLifecycleOwner) {
            it.data?.let { countryResponse ->
                binding.progressBar.visibility = View.GONE
                populateCountryList(countryResponse.data)
                countryViewModel.getCurrentCountyLocation()
            } ?: kotlin.run {
                when(it.status){
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {}
                    Status.ERROR,
                    Status.FAILURE,
                    Status.NO_INTERNET ->{
                        context?.showToast("Not able to fetch country data")
                    }
                }
            }
        }

        countryViewModel.currentCountryLocationCode.observe(viewLifecycleOwner) {
            it.data?.let { locationResponse ->
                handleCurrentLocation(locationResponse.countryCode)
            } ?: kotlin.run {
                when(it.status){
                    Status.LOADING -> {}
                    Status.SUCCESS -> {}
                    Status.ERROR,
                    Status.FAILURE,
                    Status.NO_INTERNET ->{
                        context?.showToast("Not able to fetch country data")
                    }
                }
            }
        }

        authViewModel.signUpResponse.observe(viewLifecycleOwner) {
            if (it == true){
                context?.showToast("Sign up successfully. Please login now")
                activity?.setResult(AppCompatActivity.RESULT_OK)
                activity?.finish()
            }else{
                context?.showToast("Sign up failed. Please try again")
            }
        }
    }

    private fun populateCountryList(countryData: Map<String, CountryInfo>?) {
        if (!countryData.isNullOrEmpty()){
            binding.signupForm.visibility = View.VISIBLE
        }
        countryData?.let { data ->
            val countryNameList = data.values.map {
                it.country
            }
            val adapter = ArrayAdapter(context ?: requireContext(), android.R.layout.simple_spinner_item, countryNameList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.countrySpinner.setAdapter(adapter)

        }
    }

    private fun handleCurrentLocation(countryCode: String?) {
        countryViewModel.getCurrentCountryPositionInList(countryCode).let {
            if (it!=0){
                binding.countrySpinner.setSelection(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
