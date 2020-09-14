package com.example.callofdutymw_stats.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.util.GameModeConstants
import com.example.callofdutymw_stats.util.Resource
import com.example.callofdutymw_stats.util.Status
import com.example.callofdutymw_stats.view.util.UserConstants
import com.example.callofdutymw_stats.viewmodel.UserInformationViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.autoCompleteTextViewGameMode
import kotlinx.android.synthetic.main.activity_user_information.*
import java.text.DecimalFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UserInformationActivity : AppCompatActivity() {

    private val mutableLiveData = MutableLiveData<String>()
    private var favoriteStarClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)
        supportActionBar!!.hide()

        setStarStatusAgainstUser(getSearchedUser())

        observeGameMode()
        textViewFavoriteUserClick()

        setAllUserInformations()
        setAutoCompleteGameMode()
    }

    private fun getSearchedUser(): UserInformationMultiplayer {
        return intent.getSerializableExtra(UserConstants.OBJECT_USER) as UserInformationMultiplayer
    }

    private fun setStarStatusAgainstUser(user: UserInformationMultiplayer) {
        val userInformationViewModel = UserInformationViewModel(this)
        val databaseUsers = userInformationViewModel.getAllFavoriteUsers()
        for (i in databaseUsers.indices) {
            if (userInformationViewModel.userAlreadyStarred(user, databaseUsers, i)) {
                imageViewStarFavoritePlayer.setImageResource(R.drawable.ic_baseline_star_24)
                favoriteStarClicked = true
            }
        }
    }

    private fun textViewFavoriteUserClick() {
        textViewAddUserFavorite.setOnClickListener {
            setStarStatusAndAddUser(it)
        }
    }

    private fun setStarStatusAndAddUser(view: View) {
        val userInformationViewModel = UserInformationViewModel(this)

        favoriteStarClicked = if (!favoriteStarClicked) {
            if (userInformationViewModel.starredLimitIsValid(userInformationViewModel.getAllFavoriteUsers())) {
                imageViewStarFavoritePlayer.setImageResource(R.drawable.ic_baseline_star_24)
                addUserInFavorites(getSearchedUser())

                Snackbar.make(view, R.string.added_to_favorites, Snackbar.LENGTH_LONG).show()
                true
            } else {
                Snackbar.make(view, R.string.limited_exceeded, Snackbar.LENGTH_LONG).show()
                false
            }
        } else {
            Log.e("Testing here ", "testing")
            imageViewStarFavoritePlayer.setImageResource(R.drawable.ic_baseline_star_border_outlined_24)
            deleteUserInFavorites(getSearchedUser())

            Snackbar.make(view, R.string.removed_to_favorites, Snackbar.LENGTH_LONG).show()
            false
        }
    }

    private fun addUserInFavorites(user: UserInformationMultiplayer) {
        val userInformationViewModel = UserInformationViewModel(this)
        userInformationViewModel.addUserInFavorites(user)
    }

    private fun deleteUserInFavorites(user: UserInformationMultiplayer) {
        val userInformationViewModel = UserInformationViewModel(this)
        userInformationViewModel.deleteUserInFavorites(user)
    }

    private fun setAllUserInformations() {
        setUserDefaultInformations(getSearchedUser())
        setWarzoneUserInformation(getSearchedUser().userNickname, getSearchedUser().platform)
        setMultiplayerUserInformation(getSearchedUser())
    }

    private fun setUserDefaultInformations(user: UserInformationMultiplayer) {
        textViewUserLevel.text = user.level.toString()
        textViewUserNickName.text = user.userNickname
    }

    private fun setWarzoneUserInformation(userNickname: String, platform: String) {
        val userInformationViewModel = UserInformationViewModel(this)
        userInformationViewModel.getWarzoneUser(userNickname, platform)
            .observe(this, androidx.lifecycle.Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            Log.d("Loading ", "loading...")
                        }
                        Status.SUCCESS -> {
                            setWarzoneTextViewInformations(it)
                        }
                        Status.ERROR -> {
                            Log.e("Error ", resource.message)
                        }
                    }
                }
            })
    }

    private fun setWarzoneTextViewInformations(
        it: Resource<UserDtoWarzone>?
    ) {
        val formatter = DecimalFormat("##,###,###")
        setKDArrowColor(it!!.data!!.userAllWarzone.kdRatio, imageViewWarzoneKDArrow)

        textViewWarzoneKDRatio.text = it!!.data!!.userAllWarzone.kdRatio.toString().substring(0, 4)
        textViewWarzoneTotalKills.text = formatter.format(it.data?.userAllWarzone?.kills?.toInt())
        textViewWarzoneTotalDeaths.text = formatter.format(it.data?.userAllWarzone?.deaths?.toInt())
        textViewWarzoneDowns.text = formatter.format(it.data?.userAllWarzone?.downs?.toInt())
        textViewWarzoneRevives.text = formatter.format(it.data?.userAllWarzone?.revives?.toInt())
        textViewWarzoneGamesPlayed.text =
            formatter.format(it.data?.userAllWarzone?.gamesPlayed?.toInt())
        textViewWarzoneWins.text = formatter.format(it.data?.userAllWarzone?.wins?.toInt())
        textViewWarzoneTopTwentyFive.text =
            formatter.format(it.data?.userAllWarzone?.topTwentyFive?.toInt())
        textViewWarzoneTopTen.text = formatter.format(it.data?.userAllWarzone?.topTen?.toInt())
        textViewWarzoneTopFive.text = formatter.format(it.data?.userAllWarzone?.topFive?.toInt())
        textViewWarzoneContracts.text =
            formatter.format(it.data?.userAllWarzone?.contracts?.toInt())
        textViewWarzoneScore.text = formatter.format(it.data?.userAllWarzone?.score?.toInt())
    }

    private fun setMultiplayerUserInformation(user: UserInformationMultiplayer) {
        val formatter = DecimalFormat("##,###,###")
        setKDArrowColor(user.kdRatio, imageViewKDArrow)

        if (UserInformationViewModel.responseKDRatioIsValid(user.kdRatio.toString())) {
            textViewKDRatio.text = user.kdRatio.toString().substring(0, 4)
        } else {
            textViewKDRatio.text = user.kdRatio.toString()
        }
        if (UserInformationViewModel.responseAccuracyIsValid(user.accuracy)) {
            textViewAccuracy.text =
                user.accuracy.substring(0, 4)
        } else {
            textViewAccuracy.text =
                user.accuracy
        }
        textViewTotalKills.text = formatter.format(user.totalKills.toInt())
        textViewTotalDeaths.text = formatter.format(user.totalDeaths.toInt())
        textViewHeadshots.text = formatter.format(user.headshots.toInt())
        textViewSuicides.text = formatter.format(user.suicides.toInt())
        textViewTotalAssists.text = formatter.format(user.assists.toInt())
        textViewTotalGamesPlayed.text = formatter.format(user.totalGamesPlayed.toInt())
        textViewWins.text = formatter.format(user.wins.toInt())
        textViewLosses.text = formatter.format(user.losses.toInt())
        textViewRecordKillsInMatch.text = formatter.format(user.recordKillsInMatch.toInt())
        textViewRecordDeathsInMatch.text = formatter.format(user.recordDeathsInMatch.toInt())
        textViewRecordKillStreak.text = formatter.format(user.recordKillStreak.toInt())
        textViewRecordWinStreak.text = formatter.format(user.recordWinStreak.toInt())
        textViewRecordXP.text = formatter.format(user.recordXP.toInt())
    }

    private fun setKDArrowColor(kd: Double, imageView: ImageView) {
        if (kd < 0.99) imageView.setImageResource(R.drawable.kd_arrow_down)
    }

    private fun setAutoCompleteGameMode() {
        autoCompleteTextViewGameMode.setText(GameModeConstants.MULTIPLAYER_GAME_MODE)

        val gameMode =
            arrayOf(GameModeConstants.MULTIPLAYER_GAME_MODE, GameModeConstants.WARZONE_GAME_MODE)
        autoCompleteTextViewGameMode.setAdapter(
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                gameMode
            )
        )
        setMultiplayerOrWarzoneInformations()
        autoCompleteTextViewGameMode.setOnClickListener {
            autoCompleteTextViewGameMode.showDropDown()
            setMultiplayerOrWarzoneInformations()
        }
    }

    private fun observeGameMode() {
        autoCompleteTextViewGameMode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mutableLiveData.postValue(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setMultiplayerOrWarzoneInformations() {
        mutableLiveData.observe(this, Observer {
            if (mutableLiveData.value == GameModeConstants.MULTIPLAYER_GAME_MODE) {
                linearLayoutMultiplayer.visibility = View.VISIBLE
                linearLayoutWarzone.visibility = View.GONE
            }
            if (mutableLiveData.value == GameModeConstants.WARZONE_GAME_MODE) {
                linearLayoutWarzone.visibility = View.VISIBLE
                linearLayoutMultiplayer.visibility = View.GONE
            }
        })
    }
}