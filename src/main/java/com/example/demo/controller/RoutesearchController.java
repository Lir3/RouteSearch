package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.MailForm;
import com.example.demo.service.MailService;

//ルート検索画面
@Controller
public class RoutesearchController {
	@RequestMapping(value = "/routesearch")
	public String routesearch() {
		return "routesearch";
	}

	//会社ログイン画面
	@Controller
	public class CampanyloginController {
		@RequestMapping(value = "/companylogin")
		public String campanylogin() {
			return "companylogin";
		}
	}

	/*----管理者向け----*/

	//管理者ログイン画面
	@Controller
	public class AdminloginController {
		@RequestMapping(value = "/adminlogin")
		public String campanylogin() {
			return "adminlogin";
		}
	}

	//IDpass作成画面・
	@Controller
	public class AdminIdpassController {
		@RequestMapping(value = "/adminidpass")
		public String campanyidpass() {
			return "adminidpass";
		}
	}

	//IDpass作成画面・
	@Controller
	public class CreateIdpassController {
		@RequestMapping(value = "/createidpass")
		public String campanyidpass() {
			return "createidpass";
		}
	}

	//定期券代申請一覧
	@Controller
	public class CommuterPassDataController {
		@RequestMapping(value = "/commuterpassdata")
		public String routesearch() {
			return "commuterpassdata";
		}
	}

	//自転車申請一覧
	@Controller
	public class BicDataController {
		@RequestMapping(value = "/bicdata")
		public String routesearch() {
			return "bicdata";
		}
	}

	//自転車経路図
	@Controller
	public class BicRouteMapController {
		@RequestMapping(value = "/bicroutemap")
		public String routesearch() {
			return "bicroutemap";
		}
	}

	@Controller
	public class RicDataController {
		@RequestMapping(value = "/ricdata")
		public String routesearch() {
			return "ricdata";
		}
	}

	//承認選択ページ
	@Controller
	public class ApprovalController {
		@RequestMapping(value = "/approval")
		public String approval() {
			return "approval";
		}
	}

	//返信確認ページ（yes）
	@Controller
	public class RepelyCommuterPassYController {
		@RequestMapping(value = "/replycpy")
		public String crpagey() {
			return "replycpy";
		}
	}

	//返信確認ページ（no）
	@Controller
	public class RepelyCommuterPassNController {
		@RequestMapping(value = "/replycpn")
		public String crpagen() {
			return "replycpn";
		}
	}

	/*----社員向け----*/

	//ダッシュボード(社員用)
	@Controller
	public class MainMenuController {
		@RequestMapping(value = "/mainmenu")
		public String cpaf() {
			return "mainmenu";
		}
	}

	//定期代申請書画面
	@Controller
	public class CommuterPassController {
		@RequestMapping(value = "/commuterpass")
		public String cpaf() {
			return "commuterpass";
		}
	}

	///自転車通勤申請書画面
	@Controller
	public class BicycleApplicationFormController {
		@RequestMapping(value = "/bicappform")
		public String bcaf() {
			return "bicappform";
		}
	}

	//自転車保険加入証明書
	@Controller
	public class BicycleInsuranceController {
		@RequestMapping(value = "/bicinsurance")
		public String bic() {
			return "bicinsurance";
		}
	}

	//メール承認フォームページ
	@Controller
	public class MailformController {
		@RequestMapping(value = "/mailform")
		public String mailform() {
			return "mailform";
		}
	}

	@Controller
	public class AdminDashBoardController {
		@RequestMapping(value = "/admindashboard")
		public String campanylogin() {
			return "admindashboard";
		}
	}

	@Controller
	public class GeneralDashBoard1000Controller {
		@RequestMapping(value = "/generaldashboard")
		public String campanylogin() {
			return "generaldashboard";
		}
	}

	@Controller
	public class mapController {
		@RequestMapping(value = "/map")
		public String campanylogin() {
			return "map";
		}
	}

	//メール
	@Controller
	public class AllController {

		@Autowired
		private MailService mailService;

		@GetMapping("/approval")
		public String showHome(MailForm mailForm) {
			return "approval";
		}

		@PostMapping("/sendMail")
		public String sendMail(@Validated MailForm mailForm, BindingResult bindingResult) {
			//バリデーションチェック
			if (!bindingResult.hasErrors()) {
				mailService.insertMail(mailForm);
				return "sendcomp";
			} else {
				return showHome(mailForm);
			}
		}

		@GetMapping("/back")
		public String back(MailForm mailForm) {
			return showHome(mailForm);
		}
	}
}
