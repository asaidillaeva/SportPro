package com.kotlin.sportpro.ui.profile.coach.sportsman

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.profile.Player
import com.kotlin.sportpro.data.model.profile.User
import com.kotlin.sportpro.ui.profile.UserViewModel
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.activity_add_sportsman.*
import kotlinx.android.synthetic.main.activity_add_sportsman.ageText
import kotlinx.android.synthetic.main.activity_add_sportsman.cityProfile
import kotlinx.android.synthetic.main.activity_add_sportsman.genderSpinner
import kotlinx.android.synthetic.main.activity_add_sportsman.middlenameText
import kotlinx.android.synthetic.main.activity_add_sportsman.nameText
import kotlinx.android.synthetic.main.activity_add_sportsman.phoneNumberProfile
import kotlinx.android.synthetic.main.activity_add_sportsman.profilePhotoCard
import kotlinx.android.synthetic.main.activity_add_sportsman.surnameText


class AddSportsmanActivity : AppCompatActivity() {

    private var listOfGenders = mutableListOf("Женщина", "Мужчина")
    var gender = ""
    var errorMsg = "Это поле должно быть заполнено!"

    private val viewModel: UserViewModel by viewModels {
        InjectorObject.getUserViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sportsman)

        ArrayAdapter.createFromResource(
            this, R.array.genders, R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            genderSpinner.adapter = adapter
        }

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                genderTv.text = listOfGenders[position]
            }
        }
        profilePhotoCard.setOnClickListener {

        }


        btnSave.setOnClickListener {
            if (validate(nameText) && validate(surnameText) &&
                validate(ageText) && validate(middlenameText) &&
                validate(phoneNumberProfile) && validate(cityProfile) &&
                validate(weightProfile) && validateSpinners()
            ) save()
        }
    }

    private fun save() {
        val trainerId = 38
        val trainer: User = viewModel.getCurrentUser()
        val name = nameText.text.trim().toString()
        val surname = surnameText.text.trim().toString()
        val middlename = middlenameText.text.trim().toString()
        val age = ageText.text.trim() as Int
        val phone = phoneNumberProfile.text.toString()
        var weight = weightProfile.text!!.trim().toString()

        val user = User(age = age, sex = genderTv.text.toString(), name = name, surname =  surname, middlename = middlename, phone = phone,
            organization = trainer.organization, photo = "",region = trainer.region, role = trainer.role, sport = trainer.sport, document = "" )

        var playercategory = 0
        if(age <= 7 ){
            playercategory = 0
        }



        val player = Player(
            user = user,
            trainer = trainer.id!!,
            weight = weight as Int,
            city = cityProfile.text.trim().toString(),
            organization = trainer.organization, photo = "")

        viewModel.registration(player)
        viewModel.codeResponse.observe(this, {
            if (it.isSuccessful && it.code() == 202) {
                Toast.makeText(this, "$name $surname Успешно добавлен", Toast.LENGTH_LONG)
                    .show()
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Ошибка при добавлении $name $surname",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun validateSpinners(): Boolean {
        if (gender == "") {
            genderTv.setError(errorMsg, resources.getDrawable(R.drawable.ic_error))
            return false
        }
        return true
    }

    private fun validate(editText: EditText): Boolean {
        if (editText.text.trim().isEmpty()) {
            editText.setError(errorMsg, resources.getDrawable(R.drawable.ic_error))
            return false
        }
        return true
    }
}