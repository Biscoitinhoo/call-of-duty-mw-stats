package com.example.callofdutymw_stats.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.callofdutymw_stats.R

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
        return inflater.inflate(R.layout.fragment_games, container, false)
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