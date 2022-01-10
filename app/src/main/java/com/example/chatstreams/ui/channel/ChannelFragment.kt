package com.example.chatstreams.ui.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.chatstreams.R
import com.example.chatstreams.databinding.FragmentChannelBinding
import com.example.chatstreams.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.core.internal.InternalStreamChatApi
import io.getstream.chat.android.ui.channel.list.header.viewmodel.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.channel.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory

@AndroidEntryPoint
class ChannelFragment : BindingFragment<FragmentChannelBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentChannelBinding::inflate


    private val viewModel: ChannelViewModel by activityViewModels()


    @OptIn(InternalStreamChatApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = viewModel.getUser()
        if (user == null) {
            findNavController().popBackStack()
            return
        }

        val factory = ChannelListViewModelFactory(
            filter = Filters.and(
                Filters.eq("type", "messaging"),
            ),
            sort = ChannelListViewModel.DEFAULT_SORT,
            limit = 30
        )

        val channelListViewModel: ChannelListViewModel by viewModels { factory }
        val channelListHeaderViewModel: ChannelListHeaderViewModel by viewModels()
        channelListViewModel.bindView(binding.channelListView, viewLifecycleOwner)
        channelListHeaderViewModel.bindView(binding.channelListHeaderView, viewLifecycleOwner)

        binding.channelListHeaderView.setOnUserAvatarClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.logout_app_message),
                Toast.LENGTH_SHORT
            ).show()

        }
        binding.channelListHeaderView.setOnUserAvatarLongClickListener {
            viewModel.logOut()
            findNavController().popBackStack()
        }

        binding.channelListHeaderView.setOnActionButtonClickListener {
            findNavController().navigate(
                R.id.action_channelFragment_to_createChannelDialog
            )
        }

        binding.channelListView.setChannelItemClickListener { channel ->
            findNavController().navigate(
                R.id.action_channelFragment_to_chatFragment,
                Bundle().apply {
                    putString(EXTRA_CHANNEL_ID, channel.cid)
                })

        }

    }

    companion object {
        const val EXTRA_CHANNEL_ID = "channelId"
    }
}