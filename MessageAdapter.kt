package com.example.myapplication

import android.content.Context
import android.provider.Telephony.Mms.Sent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.UserAdapter.UserViewHolder
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val ITEM_RECEIVE = 1
        val ITEM_SENT = 2
        private lateinit var mAuth: FirebaseAuth


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view)
        }
        else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return SentViewHolder(view)

        }
    }

    override fun getItemCount(): Int {
return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        mAuth = FirebaseAuth.getInstance()
        val currentMessage = messageList[position]
        if(mAuth.currentUser?.uid.equals(currentMessage.senderID)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage = messageList[position]
        if(holder.javaClass == SentViewHolder::class.java){
            // do the stuff for sent view holder

            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        }
        else{
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }
    class SentViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }
    class ReceiveViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_received_message)
    }


}