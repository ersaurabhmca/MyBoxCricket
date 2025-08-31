package com.example.boxcricinfo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScoreboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        val teamScoreSummary = findViewById<TextView>(R.id.team_score_summary)
        val recyclerView = findViewById<RecyclerView>(R.id.scoreboard_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val performances = intent.getParcelableArrayListExtra<PlayerPerformance>("scoreboard_list") ?: arrayListOf()
        recyclerView.adapter = ScoreboardAdapter(performances)

        val totalRuns = performances.sumOf { it.runs }
        val totalWickets = performances.count { it.isOut }
        val totalBalls = performances.sumOf { it.ballsFaced }
        teamScoreSummary.text = "Team: $totalRuns Runs, $totalWickets Wickets, $totalBalls Balls"
    }
}

