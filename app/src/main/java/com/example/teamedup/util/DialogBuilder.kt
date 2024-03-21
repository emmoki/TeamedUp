import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.teamedup.databinding.DialogFragmentSuccessPayBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DialogBuilder(val supportFragmentManager: FragmentManager) : DialogFragment() {
    private var title: String? = null
    private var firstMessage: String? = null
    private var secondMessageVisible: Boolean? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DialogFragmentSuccessPayBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog?.window?.requestFeature(STYLE_NO_FRAME)

        title?.let { binding.tvHeadMessage.text = it }
        firstMessage?.let { binding.tvSecondMessage.text = it }
        secondMessageVisible?.let { binding.llFirstMessage.isVisible = it }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(2000)
            dismiss()
        }
    }

    fun setTitle(title: String): DialogBuilder {
        this.title = title
        return this
    }

    fun setMessage(message: String): DialogBuilder {
        this.firstMessage = message
        return this
    }

    fun setSecondMessageVisible(visibility: Boolean): DialogBuilder{
        this.secondMessageVisible = visibility
        return this
    }

    fun build() {
        val dialogFragment = DialogBuilder(supportFragmentManager)
            .setTitle(title ?: "")
            .setMessage(firstMessage ?: "")
            .setSecondMessageVisible(secondMessageVisible ?: false)

        dialogFragment.show(supportFragmentManager, "CustomDialogFragment")
    }
}
