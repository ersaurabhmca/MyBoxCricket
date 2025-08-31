package com.example.boxcricinfo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StartMatchActivity : AppCompatActivity() {
    private lateinit var playerInput: EditText
    private lateinit var addButton: Button
    private lateinit var startButton: Button
    private lateinit var playerRecyclerView: RecyclerView
    private val playerList = mutableListOf<PlayerInfo>()
    private lateinit var adapter: PlayerAdapter
    private var editingPlayerIndex: Int? = null
    private val PICK_IMAGE_REQUEST = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_match)

        playerInput = findViewById(R.id.player_input)
        addButton = findViewById(R.id.add_button)
        startButton = findViewById(R.id.start_button)
        playerRecyclerView = findViewById(R.id.player_recycler_view)

        adapter = PlayerAdapter(playerList, ::removePlayer, ::editPlayer, ::changePhoto)
        playerRecyclerView.layoutManager = LinearLayoutManager(this)
        playerRecyclerView.adapter = adapter

        addButton.setOnClickListener {
            val name = playerInput.text.toString().trim()
            if (name.isNotEmpty()) {
                playerList.add(PlayerInfo(name))
                adapter.notifyItemInserted(playerList.size - 1)
                playerInput.text.clear()
                updateStartButtonState()
            }
        }

        playerInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addButton.isEnabled = !s.isNullOrBlank()
            }
        })

        startButton.setOnClickListener {
            val intent = Intent(this, LiveScoringActivity::class.java)
            intent.putParcelableArrayListExtra("player_list", ArrayList(playerList))
            startActivity(intent)
        }

        updateStartButtonState()
    }

    private fun removePlayer(position: Int) {
        playerList.removeAt(position)
        adapter.notifyItemRemoved(position)
        updateStartButtonState()
    }

    private fun editPlayer(position: Int) {
        editingPlayerIndex = position
        playerInput.setText(playerList[position].name)
        playerInput.requestFocus()
    }

    private fun changePhoto(position: Int) {
        editingPlayerIndex = position
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data?.toString()
            editingPlayerIndex?.let {
                playerList[it].photoUri = uri
                adapter.notifyItemChanged(it)
            }
        }
    }

    private fun updateStartButtonState() {
        startButton.isEnabled = playerList.isNotEmpty()
    }
}

class PlayerAdapter(
    private val players: MutableList<PlayerInfo>,
    private val onRemove: (Int) -> Unit,
    private val onEdit: (Int) -> Unit,
    private val onChangePhoto: (Int) -> Unit
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {
    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameInput: EditText = view.findViewById(R.id.player_name_input)
        val removeButton: Button = view.findViewById(R.id.remove_button)
        val editButton: Button = view.findViewById(R.id.edit_button)
        val photoView: ImageView = view.findViewById(R.id.player_photo)
        val changePhotoButton: Button = view.findViewById(R.id.change_photo_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.nameInput.setText(player.name)
        holder.nameInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                players[holder.adapterPosition].name = s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        holder.removeButton.setOnClickListener {
            onRemove(holder.adapterPosition)
        }
        holder.editButton.setOnClickListener {
            onEdit(holder.adapterPosition)
        }
        holder.changePhotoButton.setOnClickListener {
            onChangePhoto(holder.adapterPosition)
        }
        if (player.photoUri != null) {
            holder.photoView.setImageURI(Uri.parse(player.photoUri))
        } else {
            holder.photoView.setImageResource(R.drawable.ic_default_photo)
        }
    }

    override fun getItemCount(): Int = players.size
}
