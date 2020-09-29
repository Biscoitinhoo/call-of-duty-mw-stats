package com.example.callofdutymw_stats.fragment

import android.media.Image
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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GamesFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    /** Gun mode */
    private lateinit var textViewGunTotalKills: TextView
    private lateinit var textViewGunKDRatio: TextView
    private lateinit var imageViewGunKDArrow: ImageView
    private lateinit var textViewGunTotalTimePlayed: TextView
    private lateinit var textViewGunTotalDeaths: TextView
    private lateinit var textViewGunScore: TextView

    /** Domination */
    private lateinit var textViewDomTotalKills: TextView
    private lateinit var textViewDomKDRatio: TextView
    private lateinit var imageViewDomKDArrow: ImageView
    private lateinit var textViewDomTotalTimePlayed: TextView
    private lateinit var textViewDomTotalDeaths: TextView
    private lateinit var textViewDomScore: TextView

    /** Arena */
    private lateinit var textViewArenaTotalKills: TextView
    private lateinit var textViewArenaKDRatio: TextView
    private lateinit var imageViewArenaKDRatio: ImageView
    private lateinit var textViewArenaTotalTimePlayed: TextView
    private lateinit var textViewArenaTotalDeaths: TextView
    private lateinit var textViewArenaScore: TextView

    /** Infected */
    private lateinit var textViewInfectedTotalKills: TextView
    private lateinit var textViewInfectedKDRatio: TextView
    private lateinit var imageViewInfectedKDArrow: ImageView
    private lateinit var textViewInfectedTotalTimePlayed: TextView
    private lateinit var textViewInfectedTotalDeaths: TextView
    private lateinit var textViewInfectedScore: TextView

    /** Headquarter */
    private lateinit var textViewHQTotalKills: TextView
    private lateinit var textViewHQKDRatio: TextView
    private lateinit var imageViewHQKDArrow: ImageView
    private lateinit var textViewHQTotalTimePlayed: TextView
    private lateinit var textViewHQTotalDeaths: TextView
    private lateinit var textViewHQScore: TextView

    /** Ground war */
    private lateinit var textViewGroundWarTotalKills: TextView
    private lateinit var textViewGroundWarKDRatio: TextView
    private lateinit var imageViewGroundWarKDArrow: ImageView
    private lateinit var textViewGroundWarTotalTimePlayed: TextView
    private lateinit var textViewGroundWarTotalDeaths: TextView
    private lateinit var textViewGroundWarScore: TextView

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
        val view = inflater.inflate(R.layout.fragment_games, container, false)
        setGamesInformations(view)
        return view
    }

    private fun setGamesInformations(view: View) {
        val userInformationMultiplayer = activity?.intent?.getSerializableExtra(UserConstants.OBJECT_USER) as UserInformationMultiplayer
        findViews(view)

    }

    private fun findViews(view: View) {
        /** Gun mode find views */
        textViewGunTotalKills = view.findViewById(R.id.textViewGunTotalKills) as TextView
        textViewGunKDRatio = view.findViewById(R.id.textViewGunKDRatio) as TextView
        imageViewGunKDArrow = view.findViewById(R.id.imageViewGunKDArrow) as ImageView
        textViewGunTotalTimePlayed = view.findViewById(R.id.textViewGunTotalTimePlayed) as TextView
        textViewGunTotalDeaths = view.findViewById(R.id.textViewGunTotalDeaths) as TextView
        textViewGunScore = view.findViewById(R.id.textViewGunScore) as TextView
        /** Domination find views */
        textViewDomTotalKills = view.findViewById(R.id.textViewDomTotalKills) as TextView
        textViewDomKDRatio = view.findViewById(R.id.textViewDomKDRatio) as TextView
        imageViewDomKDArrow = view.findViewById(R.id.imageViewDomKDArrow) as ImageView
        textViewDomTotalTimePlayed = view.findViewById(R.id.textViewDomTotalTimePlayed) as TextView
        textViewDomTotalDeaths = view.findViewById(R.id.textViewDomTotalDeaths) as TextView
        textViewDomScore = view.findViewById(R.id.textViewDomScore) as TextView
        /** Arena find views */
        textViewArenaTotalKills = view.findViewById(R.id.textViewArenaTotalKills) as TextView
        textViewArenaKDRatio = view.findViewById(R.id.textViewArenaKDRatio) as TextView
        imageViewArenaKDRatio = view.findViewById(R.id.imageViewArenaKDArrow) as ImageView
        textViewArenaTotalTimePlayed = view.findViewById(R.id.textViewArenaTotalTimePlayed) as TextView
        textViewArenaTotalDeaths = view.findViewById(R.id.textViewArenaTotalDeaths) as TextView
        textViewArenaScore = view.findViewById(R.id.textViewArenaScore) as TextView
        /** Infected find views */
        textViewInfectedTotalKills = view.findViewById(R.id.textViewInfectedTotalKills) as TextView
        textViewInfectedKDRatio = view.findViewById(R.id.textViewInfectedKDRatio) as TextView
        imageViewInfectedKDArrow = view.findViewById(R.id.imageViewInfectedKDArrow) as ImageView
        textViewInfectedTotalTimePlayed = view.findViewById(R.id.textViewInfectedTotalTimePlayed) as TextView
        textViewInfectedTotalDeaths = view.findViewById(R.id.textViewInfectedTotalDeaths) as TextView
        textViewInfectedScore = view.findViewById(R.id.textViewInfectedScore) as TextView
        /** Headquarter find views */
        textViewHQTotalKills = view.findViewById(R.id.textViewHQTotalKills) as TextView
        textViewHQKDRatio = view.findViewById(R.id.textViewHQKDRatio) as TextView
        imageViewHQKDArrow = view.findViewById(R.id.imageViewHQKDArrow) as ImageView
        textViewHQTotalTimePlayed = view.findViewById(R.id.textViewHQTotalTimePlayed) as TextView
        textViewHQTotalDeaths = view.findViewById(R.id.textViewHQTotalDeaths) as TextView
        textViewHQScore = view.findViewById(R.id.textViewHQScore) as TextView
        /** Ground war */
        textViewGroundWarTotalKills = view.findViewById(R.id.textViewGroundWarTotalKills) as TextView
        textViewGroundWarKDRatio = view.findViewById(R.id.textViewGroundWarKDRatio) as TextView
        imageViewGroundWarKDArrow = view.findViewById(R.id.imageViewGroundWarKDArrow) as ImageView
        textViewGroundWarTotalTimePlayed = view.findViewById(R.id.textViewGroundWarTotalTimePlayed) as TextView
        textViewGroundWarTotalDeaths = view.findViewById(R.id.textViewGroundWarTotalDeaths) as TextView
        textViewGroundWarScore = view.findViewById(R.id.textViewGroundWarScore) as TextView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GamesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}