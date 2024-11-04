package org.intelehealth.app.mpower.activities.videoCall


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.openmrs.android_sdk.library.OpenmrsAndroid
import com.openmrs.android_sdk.library.models.CallTokenModel
import com.openmrs.android_sdk.library.models.Patient
import com.openmrs.android_sdk.utilities.ApplicationConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.intelehealth.app.mpower.R
import org.intelehealth.app.mpower.activities.BaseFragment
import org.intelehealth.app.mpower.databinding.FragmentVideoCallListBinding
import org.intelehealth.app.mpower.utilities.makeGone
import org.intelehealth.app.mpower.utilities.makeInvisible
import org.intelehealth.app.mpower.utilities.makeVisible
import org.intelehealth.klivekit.call.utils.CallMode
import org.intelehealth.klivekit.call.utils.IntentUtils.getCallActivityIntent
import org.intelehealth.klivekit.model.RtcArgs
import org.intelehealth.klivekit.socket.SocketManager
import org.json.JSONObject
import java.io.IOException
import kotlin.random.Random

@AndroidEntryPoint
class VideoCallsFragment : BaseFragment(),  VideoCallsRecyclerAdapter.OnCallButtonClickListener{
    private var _binding: FragmentVideoCallListBinding? = null
    private val binding get() = _binding!!

    private val socketManager = SocketManager.instance

    private val viewModel: VideoCallsViewModel by viewModels()

    companion object {
        fun newInstance(): VideoCallsFragment {
            return VideoCallsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVideoCallListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(this.activity)

        with(binding) {
            videoCallListRecyclerView.setHasFixedSize(true)
            videoCallListRecyclerView.layoutManager = linearLayoutManager
            videoCallListRecyclerView.adapter =
                VideoCallsRecyclerAdapter(
                    this@VideoCallsFragment, ArrayList(), this@VideoCallsFragment,
                )

            setupObserver()
//            fetchMembers()
            addPatients()

            videoCallSwipeLayout.setOnRefreshListener {
                fetchMembersOnRefresh("")
                videoCallSwipeLayout.isRefreshing = false
            }
        }
    }

    private fun setupObserver() {
        viewModel.patientList.observe(viewLifecycleOwner, Observer { patientList ->
            if(patientList.isNotEmpty()){
                showMemberList(patientList)
            }
        })
        viewModel.callToken.observe(viewLifecycleOwner, Observer { callToken ->
            if(viewModel.callToken.value != null && viewModel.callToken.value!!.isNotEmpty()){
                setRoomId()
            }
        })
    }

    private fun addPatients() {
        viewModel.addMembers()
    }


    fun fetchMembers() {
        viewModel.fetchMembers()
    }

    fun fetchMembersOnRefresh(query: String) {
        viewModel.fetchMembers(query)
    }

    fun fetchMembers(query: String) {
        viewModel.fetchMembers(query)
    }

    private fun showLoading() {
        with(binding) {
            callListProgressBar.makeInvisible()
            videoCallListRecyclerView.makeGone()
        }
    }

    private fun showMemberList(patients: List<Patient>) {
        with(binding) {
            callListProgressBar.makeGone()
            if (patients.isEmpty()) {
                videoCallListRecyclerView.makeGone()
                showEmptyListText()
            } else {
                (videoCallListRecyclerView.adapter as VideoCallsRecyclerAdapter).updateList(patients)
                videoCallListRecyclerView.makeVisible()
                hideEmptyListText()
            }
        }
    }

    private fun showError() {
        with(binding) {
            callListProgressBar.makeGone()
            videoCallListRecyclerView.makeGone()
        }
        showEmptyListText()
    }

    private fun showEmptyListText() {
        binding.emptyCallList.makeVisible()
        binding.emptyCallList.text = getString(R.string.search_member_no_results)
    }

    private fun hideEmptyListText() {
        binding.emptyCallList.makeGone()
    }

    override fun onCallButtonClick(patient: Patient) {
        getCallToken(patient)
    }

    private fun generateRoomID(): String {
        return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(Regex("[xy]")) { matchResult ->
            val c = matchResult.value
            val r = Random.nextInt(16)
            val v = if (c == "x") r else (r and 0x3) or 0x8
            v.toString(16)
        }
    }

    private fun getCallToken(patient: Patient) {
        viewModel.selectedPatient = patient
        viewModel.roomId = generateRoomID()
//        val did = OpenmrsAndroid.getProviderId()
        val did = "b338f57d-69a9-4884-b077-c2059baf9ba4"
        val pid = if(patient.uuid != null) patient.uuid else ""
        viewModel.onGenerateToken(CallTokenModel(did, viewModel.roomId, pid!!))
    }

    private fun setRoomId() {
        val dName = OpenmrsAndroid.getCHWName()
        val pid = if(viewModel.selectedPatient.uuid != null) viewModel.selectedPatient.uuid else ""
        val did = "b338f57d-69a9-4884-b077-c2059baf9ba4"
        val sid = socketManager.socket?.id()

        val ci : CallInfo = CallInfo(
            pid!!,
            dName,
            viewModel.roomId,
            "eIDdqPpeQPOYEx8JPUJzwv:APA91bFIYRdGjyGVX2PwZ2kknD6vD1MOibB-tnksj-d3Nf-76PfD0lojs7fbP-Ai18RZ4Fzh03MXbtgV5QDXUAB7yYNYBGWhZStQDoNTtRudDLmaCWVDZhJVWOzsRGfG4Nfnv0au__cT",
            "dr",
            "e2c29842-c7f6-4005-bd4d-c3ab7ef608d8",
            "video_call",
            "1729589171143",
            "7bb37397-9de3-4322-b1b0-550f8002ddf9",
            did,
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ2aWRlbyI6eyJyb29tSm9pbiI6dHJ1ZSwicm9vbSI6ImFlN2E5ZjkwLWVkNWYtNGE0OS1iMDM0LTlhODQyYzJmNDk5MSIsImNhblB1Ymxpc2giOnRydWUsImNhblN1YnNjcmliZSI6dHJ1ZSwiZXhwIjoiMTAgZGF5cyJ9LCJpYXQiOjE3Mjk1ODkxNjksIm5iZiI6MTcyOTU4OTE2OSwiZXhwIjoxNzI5NjEwNzY5LCJpc3MiOiJkZXZrZXkiLCJzdWIiOiIyOGNlYTRhYi0zMTg4LTQzNGEtODJmMC0wNTUxMzMwOTBhMzgiLCJqdGkiOiIyOGNlYTRhYi0zMTg4LTQzNGEtODJmMC0wNTUxMzMwOTBhMzgifQ.Z10aL6XLNcZZ_3FDDShXvlcTaWdBQ7sGS615Hoxc0sA",
            sid!!
        )

        val callInfoJson = Gson().toJson(ci)

        val callJsonData = JSONObject().apply {
            put("nurseId", callInfoJson)
//            put("nurseId", pid)
            put("doctorName", dName)
            put("roomId", viewModel.roomId)
        }
        socketManager.emit(SocketManager.EVENT_CALL, callJsonData)

        val jsonData1 = JSONObject().apply {
            put("patientId", viewModel.roomId)
            put("connectToDrId", pid)
            put("hwName", dName)
        }
        socketManager.emit(SocketManager.EVENT_CREATE_OR_JOIN_HW, jsonData1)

        /*GlobalScope.launch {
            val jsonData3 = JSONObject().apply {
                put("socketId", sid)
                put("connectToDrId", pid)
            }
            socketManager.emit(SocketManager.EVENT_CALL_CANCEL_BY_HW, jsonData3)
        }
        Thread.sleep(3000L)*/
    }

    private fun sendFCMNotification(token: String, title: String, body: String) {
        val client = OkHttpClient()
        val serverKey = ApplicationConstants.FIREBASE_OAUTH_TOKEN
        val json = """
        {
            "to": "$token",
            "notification": {
                "title": "$title",
                "body": "$body"
            },
            "data": {
                "score": "5x1",
                "time": "15:10"
              },
        }
    """.trimIndent()

        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaType(), json)
        val request = Request.Builder()
            .url("https://fcm.googleapis.com/fcm/send")
            .addHeader("Authorization", "key=$serverKey")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                println(response.body?.string())
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


data class CallInfo (
    val nurseID: String,
    val doctorName: String,
    val roomID: String,
    val deviceToken: String,
    val initiator: String,
    val id: String,
    val type: String,
    val timestamp: String,
    val visitID: String,
    val doctorID: String,
    val appToken: String,
    val socketID: String
)


