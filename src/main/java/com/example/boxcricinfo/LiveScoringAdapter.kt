package com.example.boxcricinfo

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LiveScoringAdapter(
    private val players: List<PlayerPerformance>,
    private val onScoreChanged: () -> Unit
) : RecyclerView.Adapter<LiveScoringAdapter.PlayerViewHolder>() {
    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView = view.findViewById(R.id.player_photo)
        val name: TextView = view.findViewById(R.id.player_name)
        val stats: TextView = view.findViewById(R.id.player_stats)
        val btnRun0: Button = view.findViewById(R.id.btn_run_0)
        val btnRun1: Button = view.findViewById(R.id.btn_run_1)
        val btnRun2: Button = view.findViewById(R.id.btn_run_2)
        val btnRun3: Button = view.findViewById(R.id.btn_run_3)
        val btnRun4: Button = view.findViewById(R.id.btn_run_4)
        val btnRun6: Button = view.findViewById(R.id.btn_run_6)
        val btnWicket: Button = view.findViewById(R.id.btn_wicket)
        val btnCatch: Button = view.findViewById(R.id.btn_catch)
        val btnRunOut: Button = view.findViewById(R.id.btn_run_out)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_live_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        val info = player.playerInfo
        holder.name.text = info.name
        if (info.photoUri != null) {
            holder.photo.setImageURI(Uri.parse(info.photoUri))
        } else {
            holder.photo.setImageResource(R.drawable.ic_default_photo)
        }
        holder.stats.text = "Runs: ${player.runs} | Balls: ${player.ballsFaced} | Out: ${if (player.isOut) "Yes" else "No"}\n" +
            "Catches: ${player.catches} | Run Outs: ${player.runOuts}"

        // Batting buttons without confirmation
        holder.btnRun0.setOnClickListener { scoreRun(holder, position, 0) }
        holder.btnRun1.setOnClickListener { scoreRun(holder, position, 1) }
        holder.btnRun2.setOnClickListener { scoreRun(holder, position, 2) }
        holder.btnRun3.setOnClickListener { scoreRun(holder, position, 3) }
        holder.btnRun4.setOnClickListener { scoreRun(holder, position, 4, isFour = true) }
        holder.btnRun6.setOnClickListener { scoreRun(holder, position, 6, isSix = true) }
        holder.btnWicket.setOnClickListener { confirmAndWicket(holder, position) }

        // Fielding buttons with confirmation
        holder.btnCatch.setOnClickListener { confirmAndFielding(holder, position, isCatch = true) }
        holder.btnRunOut.setOnClickListener { confirmAndFielding(holder, position, isRunOut = true) }
    }

    private fun scoreRun(holder: PlayerViewHolder, position: Int, run: Int, isFour: Boolean = false, isSix: Boolean = false) {
        // Validation: Do not update runs if player is out
        if (players[position].isOut) return
        players[position].runs += run
        players[position].ballsFaced++
        if (isFour) players[position].fours++
        if (isSix) players[position].sixes++
        notifyItemChanged(position)
        onScoreChanged()
    }

    private fun confirmAndWicket(holder: PlayerViewHolder, position: Int) {
        val context = holder.itemView.context
        android.app.AlertDialog.Builder(context)
            .setTitle("Confirm Wicket")
            .setMessage("Mark ${players[position].playerInfo.name} as OUT?")
            .setPositiveButton("Yes") { _, _ ->
                players[position].isOut = true
                notifyItemChanged(position)
                onScoreChanged()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun confirmAndFielding(holder: PlayerViewHolder, position: Int, isCatch: Boolean = false, isRunOut: Boolean = false) {
        val context = holder.itemView.context
        val action = if (isCatch) "Catch" else "Run Out"
        android.app.AlertDialog.Builder(context)
            .setTitle("Confirm Fielding")
            .setMessage("Assign $action to ${players[position].playerInfo.name}?")
            .setPositiveButton("Yes") { _, _ ->
                if (isCatch) players[position].catches++
                if (isRunOut) players[position].runOuts++
                notifyItemChanged(position)
                onScoreChanged()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun getItemCount(): Int = players.size
}
