package org.techtown.handtxver1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup // inflate 작업에서 ViewGroup 생성을 위한 기본 라이브러리
import androidx.databinding.DataBindingUtil // databinding 변수의 inflate 작업에 필요한 메서드 생성을 위함
import org.techtown.handtxver1.databinding.FragmentBottomMenuBarBinding // databinding 변수 선언 클래스 생성을 위함
import androidx.core.view.children // sequence 에서 sequence 의 요소들에 접근하기 위한 children 프로퍼티 생성을 위함
import android.content.Intent // activity 전환을 위한 Intent 메서드 생성을 위함
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.QuestionnaireMainPage

/**
 * A simple [Fragment] subclass.
 * Use the [BottomMenuBar.newInstance] factory method to
 * create an instance of this fragment.
 */
class BottomMenuBar(private var presentMenuElementNumber: Int = 0) : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentBottomMenuBarBinding // Data binding 변수 선언

    // 각 버튼이 선택된 상태를 저장하고, 이에 따라 해당 fragment 를 사용하는 모든 화면에서 선택 여부를 노란색으로 표기하고자 함
    // 이 과정에서 sharedPrefereneces 를 사용하는 방식을 생각해보았으나, 이 경우는 뒤로가기 버튼을 이용하여 메뉴를 이동하는 등
    // 버튼을 사용하지 않고 이동되는 경우에선 오류를 해결할 수 없음
    // 따라서 아예 파라미터에 숫자를 부여하면서 해당 메뉴바 프래그먼트를 다른 액티비티에서 사용하도록 하고, 이에 따라 원하는 부분을 노랗게 색칠
    // 할 수 있도록 프래그먼트의 constructor 를 생성하였음.
    // 1 -> 감정다이어리 부분 노란색 처리
    // 2 -> 라이프로그 부분 노란색 처리
    // 3 -> 홈 부분 노란색 처리 | default -> 전체 코드 에러를 잡기 위해 임시로 지정. 모두 수정 후에 삭제 예정.
    // 4 -> 설문지 부분 노란색 처리
    // 5 -> CBT 부분 노란색 처리

    // 그리고 각 버튼은 하나만 선택되는 상태를 유지해야하기 때문에 레이아웃 파일에서 radioButton 으로 변경하고, 이들을 그룹핑하도록 수정
    // 어 근데 라디오 버튼으로 만들고 아예 그 내부를 피그마에서 글씨까지 가져온 png 파일로 대체하려고 하니까 깨져서 그냥 텍스트뷰로 유지

    // 뒤로 가기를 눌렀을 때, fragment 의 현재 위치한 곳 표현을 알맞게 설정해주기 위해서 메뉴바 프래그먼트를 포함하는 모든 액티비티의
    // onResume 에 BottomMenuBar 클래스를 모두 호출해줘야 함


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, menuBar: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Data binding 변수에 대하여 작성한 xml 파일 레이아웃에 대하여 원하는 ViewGroup에 대하여 inflate 작업 진행
        // 이 때, DataBindingUtil.inflate 메서드로 진행하며, resource 에 xml 전체 파일을, ViewGroup 에 원하는 부분의 id를 적어준다
        // 이를 위해서는 원하는 ViewGroup에 대하여 xml 파일에 id가 입력되어 있어야 함
        // 마지막으로 초기화된 binding 변수의 최상위 뷰를 반환하면 된다.
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_menu_bar, menuBar, false)
        return binding.root
    }

    // onCreateView 메서드에서 반환된 뷰가 완전히 생성된 이후에 onViewCreated 메서드가 호출이 된다.
    // 다시 말해, onCreateView 메서드에서는 뷰의 레이아웃을 inflate 하여 반환하고,
    // 이후 onViewCreated 메서드에서는 이렇게 생성된 뷰에 대해 추가적인 초기화 작업을 수행함으로서 같이 사용된다.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // onViewCreated 메서드를 재정의 하되, setOnClickListener 가 실행되도록 재정의
        setOnClickListener()

        // presentMenuElementNumber 를 사용하여 노락색 칠을 알맞게 해주기 위한 함수 실행
        presentMenuElementNumberActivating()

    }

    // 클릭 이벤트에 대한 처리를 위해 onClick 관련 속성을 사용하기 위해서는 Fragment 에서는 아래와 같이
    // 직접 작성해주는 작업이 필요함. 우선 View.OnClickListener 를 상속받는 코드를 상단에서 작성하고, 아래의 코드로 재정의 해준다.

    private fun setOnClickListener() {
        // setOnClickListener 메서드를 직접 구현해주는 코드를 작성.
        // 여기에서 우리는 inflate 한 view 객체인 binding 에서 menubar 라는 ViewGroup 에 접근하여
        // 이들의 자식 View 들을 Sequence 로서 가져올 수 있음
        val menuElementSequence = binding.menuBar.children
        menuElementSequence.forEach { menuElement ->
            // View 객체의 setOnClickListener 메서드(위에 선언하고 있는 동명의 메서드와는 다름) 는 실제로
            // 파라미터로 특정한 클래스가 올 수 있고, 그 클래스에서 구현되는 onClick 메서들를 실행하도록 되어있다.
            // 여기에서 this 는 BottomMenuBar 이고, 해당 클래스에서는 View.OnClickListener 인터페이스를 상속받고 있다.
            // View.OnClickListener 에서는 onClick 메서드를 선언만 하고 있고, 아래의 코드에서 BottomMenuBar 에서 해당 메서드를
            // 구체적으로 구현하고 있다.

            // 좀 더 구체적으로는, 지금까지 setOnClickListener 를 사용했던 방식은 해당 메서드 이름을 그저 그렇게 사용하고 나서
            // 람다식으로 실행 내용을 적어준 것이고, View.setOnClickListener(View.onClickListener) 는 파마미터에 오는
            // 객체에 대하여 onClickListener 인터페이스의 onClick 메서드 구현부를 실행하는 것이다.
            menuElement.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.menuBarElement1 -> {
                val intentMenuElement1 = Intent(requireActivity(), EmotionDiary::class.java)
                startActivity(intentMenuElement1)
            }
            R.id.menuBarElement2 -> {
                val intentMenuElement2 = Intent(requireActivity(), LifeLogData1::class.java)
                startActivity(intentMenuElement2)
            }
            R.id.menuBarElement3 -> {
                val intentMenuElement3 = Intent(requireActivity(), MyDataLoading::class.java)
                startActivity(intentMenuElement3)
            }
            R.id.menuBarElement4 -> {
                val intentMenuElement4 =
                    Intent(requireActivity(), QuestionnaireMainPage::class.java)
                startActivity(intentMenuElement4)
            }
            R.id.menuBarElement5 -> {
                val intentMenuElement5 = Intent(requireActivity(), CbtPage1::class.java)
                startActivity(intentMenuElement5)
            }
        }
    }

    private fun presentMenuElementNumberActivating() {
        when(presentMenuElementNumber) {
            0 -> {
                binding.menuBarElement1.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement2.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement3Top.background = ContextCompat.getDrawable(requireContext(), R.drawable.gray_circle_hat)
                binding.menuBarElement3Bottom.background = ContextCompat.getDrawable(requireContext(), R.drawable.home_button_background)
                binding.menuBarElement4.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement5.background = ColorDrawable(Color.parseColor("#00ffffff"))
            }
            1 -> {
                binding.menuBarElement1.background = ColorDrawable(Color.parseColor("#fceeaf"))
                binding.menuBarElement2.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement3Top.background = ContextCompat.getDrawable(requireContext(), R.drawable.gray_circle_hat)
                binding.menuBarElement3Bottom.background = ContextCompat.getDrawable(requireContext(), R.drawable.home_button_background)
                binding.menuBarElement4.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement5.background = ColorDrawable(Color.parseColor("#00ffffff"))
            }
            2 -> {
                binding.menuBarElement1.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement2.background = ColorDrawable(Color.parseColor("#fceeaf"))
                binding.menuBarElement3Top.background = ContextCompat.getDrawable(requireContext(), R.drawable.gray_circle_hat)
                binding.menuBarElement3Bottom.background = ContextCompat.getDrawable(requireContext(), R.drawable.home_button_background)
                binding.menuBarElement4.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement5.background = ColorDrawable(Color.parseColor("#00ffffff"))
            }
            3 -> {
                binding.menuBarElement1.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement2.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement3Top.background = ContextCompat.getDrawable(requireContext(), R.drawable.gray_circle_hat_filled_yellow)
                binding.menuBarElement3Bottom.background = ContextCompat.getDrawable(requireContext(), R.drawable.home_button_background_filled_yellow)
                binding.menuBarElement4.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement5.background = ColorDrawable(Color.parseColor("#00ffffff"))
            }
            4 -> {
                binding.menuBarElement1.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement2.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement3Top.background = ContextCompat.getDrawable(requireContext(), R.drawable.gray_circle_hat)
                binding.menuBarElement3Bottom.background = ContextCompat.getDrawable(requireContext(), R.drawable.home_button_background)
                binding.menuBarElement4.background = ColorDrawable(Color.parseColor("#fceeaf"))
                binding.menuBarElement5.background = ColorDrawable(Color.parseColor("#00ffffff"))
            }
            5 -> {
                binding.menuBarElement1.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement2.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement3Top.background = ContextCompat.getDrawable(requireContext(), R.drawable.gray_circle_hat)
                binding.menuBarElement3Bottom.background = ContextCompat.getDrawable(requireContext(), R.drawable.home_button_background)
                binding.menuBarElement4.background = ColorDrawable(Color.parseColor("#00ffffff"))
                binding.menuBarElement5.background = ColorDrawable(Color.parseColor("#fceeaf"))
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BottomMenuBar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            BottomMenuBar().apply {
                arguments = Bundle().apply {

                }
            }
    }
}