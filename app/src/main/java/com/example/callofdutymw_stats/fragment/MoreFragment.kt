package com.example.callofdutymw_stats.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.view.util.UserConstants

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MoreFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var textViewAccuracy: TextView
    private lateinit var textViewHeadshots: TextView
    private lateinit var textViewSuicides: TextView
    private lateinit var textViewAssists: TextView
    private lateinit var textViewRecordDeathsInMatch: TextView
    private lateinit var textViewRecordWinStreak: TextView
    private lateinit var textViewRecordXP: TextView
    private lateinit var textViewTotalShots: TextView
    private lateinit var textViewTotalShotsMisses: TextView
    private lateinit var textViewTotalShotsHits: TextView


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
        val view = inflater.inflate(R.layout.fragment_more, container, false)
        setGeneralUserInformations(view)
        return view
    }

    private fun setGeneralUserInformations(view: View) {
        val userInformationMultiplayer = activity?.intent?.getSerializableExtra(UserConstants.OBJECT_USER) as UserInformationMultiplayer
        findViews(view)
        textViewAccuracy.text = userInformationMultiplayer.accuracy
        textViewHeadshots.text = userInformationMultiplayer.headshots
        textViewSuicides.text = userInformationMultiplayer.suicides
        textViewAssists.text = userInformationMultiplayer.assists
        textViewRecordDeathsInMatch.text = userInformationMultiplayer.recordDeathsInMatch
        textViewRecordWinStreak.text = userInformationMultiplayer.recordWinStreak
        textViewRecordXP.text = userInformationMultiplayer.recordXP
        textViewTotalShots.text = userInformationMultiplayer.totalShots.toString()
        textViewTotalShotsMisses.text = userInformationMultiplayer.totalShotsMisses.toString()
        textViewTotalShotsHits.text = userInformationMultiplayer.totalShotsHits.toString()
    }

    private fun findViews(view: View) {
        textViewAccuracy = view.findViewById(R.id.textViewAccuraccy) as TextView
        textViewHeadshots = view.findViewById(R.id.textViewHeadshots) as TextView
        textViewSuicides = view.findViewById(R.id.textViewSuicides) as TextView
        textViewAssists = view.findViewById(R.id.textViewAssists) as TextView
        textViewRecordDeathsInMatch = view.findViewById(R.id.textViewRecordDeathsInMatch) as TextView
        textViewRecordWinStreak = view.findViewById(R.id.textViewRecordWins) as TextView
        textViewRecordXP = view.findViewById(R.id.textViewTotalXP) as TextView
        textViewTotalShots = view.findViewById(R.id.textViewTotalShots) as TextView
        textViewTotalShotsMisses = view.findViewById(R.id.textViewTotalShotsMisses) as TextView
        textViewTotalShotsHits = view.findViewById(R.id.textViewTotalShotsHits) as TextView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}