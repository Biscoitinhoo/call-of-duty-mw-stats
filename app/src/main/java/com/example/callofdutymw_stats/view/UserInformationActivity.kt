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
import com.example.callofdutymw_stats.fragment.GeneralFragment
import com.example.callofdutymw_stats.fragment.MoreFragment
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.util.GameModeConstants
import com.example.callofdutymw_stats.util.Resource
import com.example.callofdutymw_stats.util.Status
import com.example.callofdutymw_stats.view.adapter.ViewPagerAdapter
import com.example.callofdutymw_stats.view.util.UserConstants
import com.example.callofdutymw_stats.viewmodel.MainActivityViewModel
import com.example.callofdutymw_stats.viewmodel.UserInformationViewModel
import kotlinx.android.synthetic.main.activity_main.autoCompleteTextViewPlatforms
import kotlinx.android.synthetic.main.activity_user_information.*
import java.text.DecimalFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UserInformationActivity : AppCompatActivity() {

    private val mutableLiveData = MutableLiveData<String>()

    private lateinit var generalFragment: GeneralFragment
    private lateinit var moreFragment: MoreFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)
        supportActionBar!!.hide()

        setTopTabClicks()

        addUserInHistoric()

        observeGameMode()

        setAllUserInformations()
        setAutoCompleteGameMode()
    }

    private fun setTopTabClicks() {
        generalFragment = GeneralFragment()
        moreFragment = MoreFragment()

        tabLayout.setupWithViewPager(viewPager)

        val viewPageAdapter = ViewPagerAdapter(supportFragmentManager, 0)
        viewPageAdapter.addFragment(generalFragment, "Geral")
        viewPageAdapter.addFragment(moreFragment, "Mais")
        viewPager.adapter = viewPageAdapter
    }

    private fun addUserInHistoric() {
        val userInformationViewModel = UserInformationViewModel(this)
        val historicList = userInformationViewModel.getAllUsersInHistoric()
        var userAlreadyInHistory = false

        if (historicList.isEmpty()) {
            addUserInHistoric(getSearchedUser())
        } else {
            for (i in historicList.indices) {
                if (userInformationViewModel.userAlreadyInHistoric(
                        getSearchedUser(),
                        historicList,
                        i
                    )
                ) {
                    userAlreadyInHistory = true
                }
            }
            if (!userAlreadyInHistory && userInformationViewModel.historicLimitIsValid(historicList)) {
                addUserInHistoric(getSearchedUser())
            }
        }
    }

    private fun getSearchedUser(): UserInformationMultiplayer {
        return intent.getSerializableExtra(UserConstants.OBJECT_USER) as UserInformationMultiplayer
    }

    private fun addUserInHistoric(user: UserInformationMultiplayer) {
        val userInformationViewModel = UserInformationViewModel(this)
        userInformationViewModel.addUserInHistoric(user)
    }

    private fun deleteUserInHistoric(user: UserInformationMultiplayer) {
        val userInformationViewModel = UserInformationViewModel(this)
        userInformationViewModel.deleteUserInHistoric(user)
    }

    private fun setAllUserInformations() {
        val mainActivityViewModel = MainActivityViewModel()

        setUserDefaultInformations(getSearchedUser())
        val userPlatform =
            mainActivityViewModel.setExtendedPlatformToDefault(getSearchedUser().platform)
        setWarzoneUserInformation(getSearchedUser().userNickname, userPlatform)
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
                            Log.e("Error in UserInformationActivity ", resource.message)
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

        textViewWarzoneKDRatio.text = it.data!!.userAllWarzone.kdRatio.toString().substring(0, 4)
        textViewWarzoneTotalKills.text = formatter.format(it.data.userAllWarzone.kills.toInt())
        textViewWarzoneTotalDeaths.text = formatter.format(it.data.userAllWarzone.deaths.toInt())
        textViewWarzoneDowns.text = formatter.format(it.data.userAllWarzone.downs.toInt())
        textViewWarzoneRevives.text = formatter.format(it.data.userAllWarzone.revives.toInt())
        textViewWarzoneGamesPlayed.text =
            formatter.format(it.data.userAllWarzone.gamesPlayed.toInt())
        textViewWarzoneWins.text = formatter.format(it.data.userAllWarzone.wins.toInt())
        textViewWarzoneTopTwentyFive.text =
            formatter.format(it.data.userAllWarzone.topTwentyFive.toInt())
        textViewWarzoneTopTen.text = formatter.format(it.data.userAllWarzone.topTen.toInt())
        textViewWarzoneTopFive.text = formatter.format(it.data.userAllWarzone.topFive.toInt())
        textViewWarzoneContracts.text =
            formatter.format(it.data.userAllWarzone.contracts.toInt())
        textViewWarzoneScore.text = formatter.format(it.data.userAllWarzone.score.toInt())
    }

    private fun setKDArrowColor(kd: Double, imageView: ImageView) {
        if (kd < 0.99) imageView.setImageResource(R.drawable.kd_arrow_down)
    }

    private fun setAutoCompleteGameMode() {
        autoCompleteTextViewPlatforms.setText(GameModeConstants.MULTIPLAYER_GAME_MODE)

        val gameMode =
            arrayOf(GameModeConstants.MULTIPLAYER_GAME_MODE, GameModeConstants.WARZONE_GAME_MODE)
        autoCompleteTextViewPlatforms.setAdapter(
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                gameMode
            )
        )
        setMultiplayerOrWarzoneInformations()
        autoCompleteTextViewPlatforms.setOnClickListener {
            autoCompleteTextViewPlatforms.showDropDown()
            setMultiplayerOrWarzoneInformations()
        }
    }

    private fun observeGameMode() {
        autoCompleteTextViewPlatforms.addTextChangedListener(object : TextWatcher {
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