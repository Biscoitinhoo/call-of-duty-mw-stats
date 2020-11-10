package com.example.callofdutymw_stats.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.view.util.UserConstants
import com.example.callofdutymw_stats.viewmodel.UserInformationViewModel
import kotlinx.android.synthetic.main.fragment_general.*
import java.text.DecimalFormat

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GeneralFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var textViewTotalGamesPlayed: TextView
    private lateinit var textViewWins: TextView
    private lateinit var textViewLosses: TextView
    private lateinit var textViewTotalKills: TextView
    private lateinit var textViewKDRatio: TextView
    private lateinit var textViewTotalDeaths: TextView
    private lateinit var textViewAccuracy: TextView
    private lateinit var textViewRecordKillStreak: TextView
    private lateinit var imageViewKDArrow: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_general, container, false)
        setGeneralUserInformations(view)
        return view
    }

    private fun setGeneralUserInformations(view: View) {
        val user =
            activity?.intent?.getSerializableExtra(UserConstants.OBJECT_USER) as UserInformationMultiplayer
        findViews(view)

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
        textViewTotalGamesPlayed.text = formatter.format(user.totalGamesPlayed.toInt())
        textViewWins.text = formatter.format(user.wins.toInt())
        textViewLosses.text = formatter.format(user.losses.toInt())
        textViewTotalKills.text = formatter.format(user.totalKills.toInt())
        textViewTotalDeaths.text = formatter.format(user.totalDeaths.toInt())
        textViewRecordKillStreak.text = formatter.format(user.recordKillStreak.toInt())
    }

    private fun findViews(view: View) {
        textViewTotalGamesPlayed = view.findViewById(R.id.textViewTotalGamesPlayed) as TextView
        textViewWins = view.findViewById(R.id.textViewWins) as TextView
        textViewLosses = view.findViewById(R.id.textViewLosses) as TextView
        textViewTotalKills = view.findViewById(R.id.textViewTotalKills) as TextView
        textViewKDRatio = view.findViewById(R.id.textViewKDRatio) as TextView
        textViewTotalDeaths = view.findViewById(R.id.textViewTotalDeaths) as TextView
        textViewAccuracy = view.findViewById(R.id.textViewAccuracy) as TextView
        textViewRecordKillStreak = view.findViewById(R.id.textViewRecordKillStreak) as TextView
        imageViewKDArrow = view.findViewById(R.id.imageViewKDArrow) as ImageView
    }

    private fun setKDArrowColor(kd: Double, imageView: ImageView) {
        if (kd < 0.99) imageView.setImageResource(R.drawable.kd_arrow_down)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GeneralFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}