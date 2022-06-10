package com.kotlin.sportpro.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Region
import com.kotlin.sportpro.data.model.Sport
import com.kotlin.sportpro.data.model.profile.Player
import com.kotlin.sportpro.data.model.profile.User
import com.kotlin.sportpro.ui.home.SportViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import com.kotlin.sportpro.utils.Utils
import kotlinx.android.synthetic.main.activity_profile_info.*


class ProfileInfoActivity : AppCompatActivity() {
    private var listOfGenders = mutableListOf("Женщина", "Мужчина")
    private var listOfSports = mutableListOf<Sport>()
    private var listOfRegions = mutableListOf<Region>()
    private var listOfCategory = mutableListOf("Олимпийские", "Неолимпийские", "Пара и Сурдо", "Национальные")

    private val sportViewModel: SportViewModel by viewModels {
        InjectorObject.getSportViewModelFactory()
    }

    private val userViewModel: UserViewModel by viewModels {
        InjectorObject.getUserViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info)

        phoneNumberProfile.isEnabled = false

        sportViewModel.getAllRegions().observe(this) {
            when (it) {
                is ApiResult.Success -> {
                    listOfRegions = it.data.toMutableList()
                }
            }
        }
        if (intent.extras?.get("editable") as Boolean) {
            button_done.visibility = View.VISIBLE
            button_done.setOnClickListener {
                if (validData()) {
                    saveData()
                }
            }
            var list = mutableListOf<String>()
            for (element in listOfRegions) {
                list.add(element.name)
            }
            addItemsToSpinner(list, regionSpinner)
            regionSpinner.isEnabled = true

            addItemsToSpinner(listOfCategory, sportCategorySpinner)
            addItemsToSpinner(listOfGenders, genderSpinner)

            genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    genderTV.setText(listOfGenders[position])
                }
            }

            sportTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    sportTypeTV.text = listOfSports[position].name
                }
            }

            sportCategorySpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        sportCategoryTV.text = listOfCategory[position]
                        getSportListByCategoryId(position)
                    }

                }

            regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    oblastTV.error = "Выберите регион"
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    oblastTV.setText(listOfRegions[position].name)
                }

            }
        }

        editable(intent.extras?.get("editable") as Boolean, profileInfo as ViewGroup)
        setData()

        btnBackFromProfileInfo.setOnClickListener {
            Utils(applicationContext)
            //TODO: make Alert Dialog are you sure you want to exit
            finish()
        }
    }

    private fun saveData() {
        //TODO: Update photo
//        userViewModel.updateUser(user: User)
   }

    private fun setUserData(user: User) {
        Glide.with(this)
            .load(user.photo)
            .override(110, 110)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(true)
            .into(profilePhoto)

        nameText.setText(user.name)
        surnameText.setText(user.surname)
        middlenameText.setText(user.middlename)
        ageText.setText(user.age)
        genderTV.setText(user.sex)

        for (i in listOfRegions.indices){
            if(user.region == listOfRegions[i].id){
                oblastTV.text = listOfRegions[i].name

            }
        }
        phoneNumberProfile.setText(user.phone)
        weightProfileInfo.visibility = View.GONE
        for (i in listOfSports.indices){
            if(user.sport == listOfSports[i].id){
                sportTypeTV.text = listOfSports[i].name
                sportCategoryTV.text = listOfSports[i].category?.name
            }
        }
        organizationProfileinfo.setText(user.organization)

    }

    private fun setPlayerData(player: Player) {
        Glide.with(this)
            .load(player.photo)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(true)
            .override(100, 100)
            .into(profilePhoto)

        nameText.setText(player.user.name)
        surnameText.setText(player.user.surname)
        middlenameText.setText(player.user.middlename)
        ageText.setText(player.user.age.toString())
        genderTV.text = player.user.sex

        for (i in listOfRegions.indices){
            if(player.user.region == listOfRegions[i].id){
                oblastTV.text = listOfRegions[i].name

            }
        }
        cityProfile.setText( player.city)
        phoneNumberProfile.setText(player.user.phone)
        weightProfileInfo.setText(player.weight.toString())
        for (i in listOfSports.indices){
            if(player.user.sport == listOfSports[i].id){
                sportTypeTV.text = listOfSports[i].name
                sportCategoryTV.text = listOfSports[i].category?.name
            }
        }
        organizationProfileinfo.setText(player.user.organization)
    }

    private fun validData(): Boolean {
        var result = true
        if (nameText.text.trim().isBlank() || nameText.text.trim().length <= 1) {
            nameText.error = "Напишите более 2х букв"
            result = false
        }
        if (surnameText.text.trim().isBlank() || surnameText.text.trim().length <= 1) {
            surnameText.error = "Напишите более 2х букв"
            result = false
        }
        if (middlenameText.text.trim().isBlank() || middlenameText.text.trim().length <= 1) {
            middlenameText.error = "Напишите более 2х букв"
            result = false
        }
        if (ageText.text.trim().isBlank()) result = false
        if (cityProfile.text.trim().isBlank() || cityProfile.text.trim().length <= 1) {
            result = false
            cityProfile.error = "Напишите более 2х букв"
        }
        if (organizationProfileinfo.text.trim().isBlank()) {
            result = false
            organizationProfileinfo.error = "Заполните это поле"
        }
//        if (phoneNumberProfile.text?.trim().isNullOrEmpty() || phoneNumberProfile.text?.trim()?.filter { it.isDigit() }?.length!! != 12) {
//            phoneNumberProfile.error = "Заполните поле правильно"
//            result = Patterns.PHONE.matcher(phoneNumberProfile.text?.trim()).matches()
//        }

        return result
    }

    fun editable(enable: Boolean, vg: ViewGroup) {
        for (child in vg.children) {
            child.isEnabled = enable
            if (child is ViewGroup) {
                editable(enable, child)
            }
        }
    }

    private fun setData() {

        var id = intent.extras?.get("userId")
        if(id == null){
            id = intent.extras?.get("playerId") as Int
            userViewModel.getPlayerById(id).observe(this){
                when (it){
                    is ApiResult.Success ->{
                        setPlayerData(it.data)
                    }
                }
            }

        }else{
            userViewModel.getUserById(id as Int).observe(this){
                when(it){
                    is ApiResult.Success -> {
                        setUserData(it.data)

                    }
                }
            }
        }
    }

    private fun addItemsToSpinner(list: MutableList<String>, spinner: Spinner) {
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.spinner_item, list
        )
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = dataAdapter
    }

    private fun getSportListByCategoryId(id: Int) {
        sportViewModel.getSportListByCategoryId(id).observe(this) {
            when (it) {
                is ApiResult.Success -> {
                   listOfSports = it.data.toMutableList()

                    var list = mutableListOf<String>()
                    for ( i in listOfSports.indices){
                        list.add(listOfSports[i].name!!)
                    }
                    addItemsToSpinner(list, sportTypeSpinner)
                }
                is ApiResult.Error -> {
                    listOfSports.clear()
                    Log.e("ProfileInfo", it.throwable.message.toString())
                }
            }
        }
    }

}