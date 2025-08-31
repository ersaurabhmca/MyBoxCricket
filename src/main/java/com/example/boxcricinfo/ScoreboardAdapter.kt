package com.example.boxcricinfo

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreboardAdapter(private val players: List<PlayerPerformance>) : RecyclerView.Adapter<ScoreboardAdapter.PlayerViewHolder>() {
    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView = view.findViewById(R.id.player_photo)
        val name: TextView = view.findViewById(R.id.player_name)
        val runs: TextView = view.findViewById(R.id.player_runs)
        val fours: TextView = view.findViewById(R.id.player_fours)
        val sixes: TextView = view.findViewById(R.id.player_sixes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_scoreboard_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        val info = player.playerInfo
        holder.name.text = info.name
        holder.runs.text = player.runs.toString()
        holder.fours.text = player.fours.toString()
        holder.sixes.text = player.sixes.toString()
        if (info.photoUri != null) {
            holder.photo.setImageURI(Uri.parse(info.photoUri))
        } else {
            holder.photo.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

    override fun getItemCount(): Int = players.size
}

