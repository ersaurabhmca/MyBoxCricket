package com.example.boxcricinfo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boxcricinfo.LiveScoringAdapter
import com.example.boxcricinfo.PlayerInfo

data class PlayerPerformance(
    val playerInfo: PlayerInfo,
    var runs: Int = 0,
    var ballsFaced: Int = 0,
    var isOut: Boolean = false,
    var wicketsTaken: Int = 0,
    var runsConceded: Int = 0,
    var ballsBowled: Int = 0,
    var catches: Int = 0,
    var runOuts: Int = 0,
    var fours: Int = 0,
    var sixes: Int = 0
) : android.os.Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        PlayerInfo(parcel.readString() ?: "", parcel.readString()),
        parcel.readInt(), // runs
        parcel.readInt(), // ballsFaced
        parcel.readByte() != 0.toByte(), // isOut
        parcel.readInt(), // wicketsTaken
        parcel.readInt(), // runsConceded
        parcel.readInt(), // ballsBowled
        parcel.readInt(), // catches
        parcel.readInt(), // runOuts
        parcel.readInt(), // fours
        parcel.readInt(), // sixes
    )

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(playerInfo.name)
        parcel.writeString(playerInfo.photoUri)
        parcel.writeInt(runs)
        parcel.writeInt(ballsFaced)
        parcel.writeByte(if (isOut) 1 else 0)
        parcel.writeInt(wicketsTaken)
        parcel.writeInt(runsConceded)
        parcel.writeInt(ballsBowled)
        parcel.writeInt(catches)
        parcel.writeInt(runOuts)
        parcel.writeInt(fours)
        parcel.writeInt(sixes)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : android.os.Parcelable.Creator<PlayerPerformance> {
        override fun createFromParcel(parcel: android.os.Parcel): PlayerPerformance {
            return PlayerPerformance(parcel)
        }

        override fun newArray(size: Int): Array<PlayerPerformance?> {
            return arrayOfNulls(size)
        }
    }
}

class LiveScoringActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LiveScoringAdapter
    private lateinit var teamSummary: TextView
    private val playerPerformances = mutableListOf<PlayerPerformance>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_scoring)

        recyclerView = findViewById(R.id.player_recycler_view)
        teamSummary = findViewById(R.id.team_summary)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val players = intent.getParcelableArrayListExtra<PlayerInfo>("player_list") ?: arrayListOf()
        playerPerformances.addAll(players.map { PlayerPerformance(it) })

        adapter = LiveScoringAdapter(playerPerformances) { updateTeamSummary() }
        recyclerView.adapter = adapter
        updateTeamSummary()

        findViewById<Button>(R.id.end_match_button).setOnClickListener {
            val intent = Intent(this, ScoreboardActivity::class.java)
            intent.putParcelableArrayListExtra("scoreboard_list", ArrayList(playerPerformances))
            startActivity(intent)
        }
    }

    private fun updateTeamSummary() {
        val totalRuns = playerPerformances.sumOf { it.runs }
        val totalWickets = playerPerformances.count { it.isOut }
        val totalBalls = playerPerformances.sumOf { it.ballsFaced }
        teamSummary.text = "Team: $totalRuns Runs, $totalWickets Wickets, $totalBalls Balls"
    }
}
