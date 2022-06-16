package com.kotlin.sportpro.ui.profile.coach.sportsman

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.profile.Player
import com.kotlin.sportpro.data.model.profile.User
import com.kotlin.sportpro.ui.profile.UserViewModel
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.activity_add_sportsman.*


class AddSportsmanActivity : AppCompatActivity() {

    private var listOfGenders = listOf<String>("Женщина", "Мужчина")
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
        }

        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, R.layout.it_dropdown, listOfGenders)
        edit_gender.setAdapter<ArrayAdapter<*>>(adapter)


        profilePhotoCard.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .compress(1024)
                .maxResultSize(800, 800)
                .start()
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
        val age = Integer.parseInt(ageText.text.toString())
        val phone = phoneNumberProfile.text.toString()
        var weight = Integer.parseInt(weightProfile.text!!.toString().replace("кг", "").trim())

        val user = User(
            age = age,
            sex = gender,
            name = name,
            surname = surname,
            middlename = middlename,
            phone = phone,
            organization = trainer.organization,
            photo = viewModel.photo,
            region = trainer.region,
            role = trainer.role,
            sport = trainer.sport,
            document = ""
        )

        var playercategory = 0
        if (age <= 7) {
            playercategory = 0
        }


        val player = Player(
            user = user,
            trainer = trainer.id!!,
            weight = weight,
            city = cityProfile.text.trim().toString(),
            organization = trainer.organization, photo = ""
        )

        viewModel.registration(player)
//        viewModel.codeResponse.observe(this) {
//            if (it.isSuccessful && it.code() == 202) {
        Toast.makeText(this, " Данные спортсмена $name $surname находятся в обработке", Toast.LENGTH_LONG).show()
        finish()
//            } else {
//                Toast.makeText(
//                    this,
//                    "Ошибка при добавлении $name $surname",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            val uri = data!!.data
            profilePhotoCard.setImageURI(uri)
            viewModel.updateAvatar(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateSpinners(): Boolean {
        if (edit_gender.text.toString() == "") {
            til_gender.error = errorMsg
            return false
        }
        gender = edit_gender.text.toString()
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