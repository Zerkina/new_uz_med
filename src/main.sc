require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: dict.csv
  name = Numbers
  var = Numbers

init:
    bind("preProcess", function($context) {
        if (!$context.session.QuestionCount) {
            $context.session.QuestionCount = 1;
        };
        if (!$context.session.PostponeQuestionCount) {
            $context.session.PostponeQuestionCount = 1;
        };
        if (!$context.session.path) {
            $context.session.path = []
        };
        if ($context.contextPath === '/Postpone') {
            $context.session.path.push('postpone')
        };
        if($context.contextPath === '/WhatCompany' && $context.session.path.indexOf('postpone') === -1 || $context.contextPath === '/Repeat' && $context.session.path.indexOf('postpone') === -1 || $context.contextPath === '/WhatName' && $context.session.path.indexOf('postpone') === -1 || $context.contextPath === '/CallGoal' && $context.session.path.indexOf('postpone') === -1 || $context.contextPath === '/Robot' && $context.session.path.indexOf('postpone') === -1 || $context.contextPath === '/PhoneNumber' && $context.session.path.indexOf('postpone') === -1 || $context.contextPath === '/WhatDate' && $context.session.path.indexOf('postpone') === -1 || $context.contextPath === '/WhatDoctor' && $context.session.path.indexOf('postpone') === -1) {
            $context.session.QuestionCount += 1
        };
        if($context.contextPath === '/WhatCompany' && $context.session.path.indexOf('postpone') != -1 || $context.contextPath === '/Repeat' && $context.session.path.indexOf('postpone') != -1 || $context.contextPath === '/WhatName' && $context.session.path.indexOf('postpone') != -1 || $context.contextPath === '/CallGoal' && $context.session.path.indexOf('postpone') != -1 || $context.contextPath === '/Robot' && $context.session.path.indexOf('postpone') != -1 || $context.contextPath === '/PhoneNumber' && $context.session.path.indexOf('postpone') != -1 || $context.contextPath === '/WhatDate' && $context.session.path.indexOf('postpone') != -1 || $context.contextPath === '/WhatDoctor' && $context.session.path.indexOf('postpone') != -1) {
            $context.session.PostponeQuestionCount += 1
        };
        if ($context.session.QuestionCount === 6 || $context.session.PostponeQuestionCount === 6) {
            $context.temp.targetState = "/OperatorHangup"
            //$reactions.transition({value: '/OperatorHangup', deferred: false});
        }; 
    });
    //Настроить перебивание?
    //bind("postProcess", function($context) {
        //$dialer.bargeInResponse({
            //bargeIn: "forced",
            //bargeInTrigger: "final",
            //noInterruptTime: 8000
       // });
    //});
    
#Тестирование словаря для номера карты и суммы. Начало
theme: /
    
    #state: Test
        #q!: $regex</start>
        
     #   state: Test1
      #      q: *
      #      script:
      #          for (var id = 1; id < Object.keys(Numbers).length + 1; id++) {
      #             if ($parseTree.text == Numbers[id].value.title) {
      #                  $reactions.audio(Numbers[id].value.url)
      #              }
      #          }
      #      audio: {{Numbers[2].value.url}}
#Тестирование словаря для номера карты и суммы. Конец

#Начало блока Медицинского бота

    state: Hello_main
        q!: $regex</start>
        script:
            $jsapi.startSession();
            log("Начало сессии")
            //вы записаны на ...даты...
            var listOfApp = ["https://storage.yandexcloud.net/medicalcentre/appointment_date/1.wav", "https://storage.yandexcloud.net/medicalcentre/appointment_date/2.wav", "https://storage.yandexcloud.net/medicalcentre/appointment_date/3.wav", "https://storage.yandexcloud.net/medicalcentre/appointment_date/13.wav", "https://storage.yandexcloud.net/medicalcentre/appointment_date/14.wav", "https://storage.yandexcloud.net/medicalcentre/appointment_date/15.wav", "https://storage.yandexcloud.net/medicalcentre/appointment_date/26.wav", "https://storage.yandexcloud.net/medicalcentre/appointment_date/27.wav", "https://storage.yandexcloud.net/medicalcentre/appointment_date/28.wav", "https://storage.yandexcloud.net/medicalcentre/appointment_date/29.wav", "https://storage.yandexcloud.net/medicalcentre/appointment_date/30.wav"]        
            var number = Math.floor(Math.random() * listOfApp.length);
            $session.app = listOfApp[number]
            //специальность врача
            var listOfSpec = ["https://storage.yandexcloud.net/medicalcentre/speciality/0.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/1.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/2.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/3.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/4.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/5.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/6.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/7.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/8.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/9.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/10.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/11.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/12.wav", "https://storage.yandexcloud.net/medicalcentre/speciality/13.wav"]
            var numberSpec = Math.floor(Math.random() * listOfSpec.length);
            $session.spec = listOfSpec[numberSpec]
            //имя врача
            var listOfName = ["https://storage.yandexcloud.net/medicalcentre/doctor_name/%D0%98%D0%B1%D1%80%D0%B0%D0%B3%D0%B8%D0%BC%D0%BE%D0%B2_%D0%A0%D1%83%D1%81%D1%82%D0%B5%D0%BC_%D0%A8%D0%B5%D0%B2%D0%BA%D0%B5%D1%82%D0%BE%D0%B2%D0%B8%D1%87.wav", "https://storage.yandexcloud.net/medicalcentre/doctor_name/%D0%98%D0%B2%D0%B0%D0%BD%D0%BE%D0%B2_%D0%98%D0%B2%D0%B0%D0%BD_%D0%98%D0%B2%D0%B0%D0%BD%D0%BE%D0%B2%D0%B8%D1%87.wav", "https://storage.yandexcloud.net/medicalcentre/doctor_name/%D0%9F%D0%B5%D1%82%D1%80%D0%BE%D0%B2_%D0%9F%D1%91%D1%82%D1%80_%D0%9F%D0%B5%D1%82%D1%80%D0%BE%D0%B2%D0%B8%D1%87.wav", "https://storage.yandexcloud.net/medicalcentre/doctor_name/%D0%A1%D0%B8%D0%B4%D0%BE%D1%80%D0%BE%D0%B2_%D0%A1%D0%B8%D0%B4%D0%BE%D1%80_%D0%A1%D0%B8%D0%B4%D0%BE%D1%80%D0%BE%D0%B2%D0%B8%D1%87.wav", "https://storage.yandexcloud.net/medicalcentre/doctor_name/%D0%A1%D1%83%D0%BB%D0%B5%D0%B9%D0%BC%D0%B0%D0%BD%D0%BE%D0%B2%D1%83%20%D0%A0%D1%83%D1%81%D0%BB%D0%B0%D0%BD%D1%83%20%D0%A0%D0%B0%D0%B2%D0%B8%D0%BB%D1%8C%D0%B5%D0%B2%D0%B8%D1%87%D1%83.wav"]
            var numberName = Math.floor(Math.random() * listOfName.length);
            $session.name = listOfName[numberName]
        audio: https://storage.yandexcloud.net/medicalcentre/hello_main_1.wav
        audio: {{$session.app}}
        audio: {{$session.spec}}
        audio: https://storage.yandexcloud.net/medicalcentre/hello_main_2.wav
    
    state: Null || noContext = true
        event!: speechNotRecognized
        if: $session.path.indexOf( 'postpone' ) != -1
            script:
                log("Тишина на переносе")
                if (!$session.PostponeNullCount) {
                    $session.PostponeNullCount = 1;
                }
                else {
                    $session.PostponeNullCount += 1;
                };
                if ($session.PostponeNullCount === 1) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/postpone_null_1.wav');
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/11_12.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/13_14.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/14_15.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_tail_1.wav");
                };
                if ($session.PostponeNullCount === 2) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/postpone_null_2.wav');
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/11_12.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/13_14.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/14_15.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_tail_2.wav");
                };
                if ($session.PostponeNullCount === 3) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/null_hangup.wav');
                    log("Тишина на переносе");
                    $dialer.setCallResult("Тишина на переносе");
                    $analytics.setSessionResult("Тишина на переносе");
                    $dialer.hangUp();
                    $jsapi.stopSession();
                }; 
        else:
            script:
                log("Тишина на приветствии")
                if (!$session.NullCount) {
                    $session.NullCount = 1;
                }
                else {
                    $session.NullCount += 1;
                };
            
                if ($session.NullCount === 1) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/hello_null_1.wav');
                };
        
                if ($session.NullCount === 2) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/hello_null_2.wav');
                    log("Тишина");
                };
                if ($session.NullCount === 3) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/null_hangup.wav');
                    log("Тишина на приветствии");
                    $dialer.setCallResult("Тишина на приветствии");
                    $analytics.setSessionResult("Тишина на приветствии");
                    $dialer.hangUp();
                    $jsapi.stopSession();
                };
            
    state: NoMatch || noContext = true
        event!: noMatch
        if: $session.path.indexOf( 'postpone' ) != -1
            script:
                log("Дефолтное возражение на переносе")
                if (!$session.PostponeNoMatchCount) {
                    $session.PostponeNoMatchCount = 1;
                }
                else {
                    $session.PostponeNoMatchCount += 1;
                };
                if ($session.PostponeNoMatchCount === 1) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_default_1.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/11_12.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/13_14.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/14_15.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_tail_1.wav");
                };
                if ($session.PostponeNoMatchCount === 2) { 
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_default_2.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/11_12.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/13_14.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/14_15.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_tail_2.wav");
                };
                if ($session.PostponeNoMatchCount === 3) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/default_hangup.wav')
                    log("Default hangup");
                    $dialer.setCallResult("Default hangup");
                    $analytics.setSessionResult("Default hangup");
                    $dialer.hangUp();
                    $jsapi.stopSession();
                };
        else:
            script:
                log("Дефолтное возражение на приветствии")
                if (!$session.noMatchCount) {
                    $session.noMatchCount = 1;
                }
                else {
                    $session.noMatchCount += 1;
                };
                if ($session.noMatchCount === 1) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/hello_default_1_1.wav');
                    $reactions.audio($session.app);
                    $reactions.audio($session.spec);
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/hello_default_1_2.wav');
                };  
               if ($session.noMatchCount === 2) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/hello_default_2.wav');
                };
                if ($session.noMatchCount === 3) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/default_hangup.wav')
                    log("Default hangup");
                    $dialer.setCallResult("Default hangup");
                    $analytics.setSessionResult("Default hangup");
                    $dialer.hangUp();
                    $jsapi.stopSession();
                };
            
    state: Voicemail
        intent!: /voicemail
        script:
            log("Автоответчик");
            $dialer.setCallResult("Автоотвечик");
            $analytics.setSessionResult("Автоответчик");
            $dialer.hangUp();
            $jsapi.stopSession();
    
    state: WhatCompany
        intent!: /what_company
        if: $session.path.indexOf( 'postpone' ) != -1
            script:
                log("Cброс на переносе - вопрос что за компания")
                if (!$session.PostponeWhatCompanyCount) {
                    $session.PostponeWhatCompanyCount = 1;
                } 
                else {
                    $session.PostponeWhatCompanyCount += 1;
                };
            if: $session.PostponeWhatCompanyCount === 1
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_company_1.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
            
            elseif: $session.PostponeWhatCompanyCount  === 2
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_company_2.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
            
            elseif: $session.PostponeWhatCompanyCount  === 3
                go!: /OperatorHangup
        else:
            #countRepeatsUsual:
                #audioMassive = ["https://storage.yandexcloud.net/medicalcentre/hello_company_1.wav", [https://storage.yandexcloud.net/medicalcentre/hello_company_2_1.wav", {{$session.app}}, {{$session.spec}}, "https://storage.yandexcloud.net/medicalcentre/hello_company_2_2.wav"]];
            script:
                log('Вопрос что за компания')
                if (!$session.WhatCompanyCount) {
                    $session.WhatCompanyCount = 1;
                }
                else {
                   $session.WhatCompanyCount += 1;
                };
                if ($session.WhatCompanyCount === 1) {
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_company_1.wav")
                };
                if ($session.WhatCompanyCount === 2) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_company_2_1.wav")
                    $reactions.audio($session.app);
                    $reactions.audio($session.spec);
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_company_2_2.wav")
                };
                if ($session.WhatCompanyCount === 3) {
                    $reactions.transition({value: '/OperatorHangup', deferred: false});
                };
    
    state: Repeat
        intent!: /repeat
        if: $session.path.indexOf( 'postpone' ) != -1
            script:
                log("Повтор на переносе")
                if (!$session.PostponeRepeatCount) {
                    $session.PostponeRepeatCount = 1;
                }
                else {
                    $session.PostponeRepeatCount += 1;
                };
            if: $session.PostponeRepeatCount === 1
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_repeat_1.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
            
            elseif: $session.PostponeRepeatCount === 2
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_repeat_2.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
            
            elseif: $session.PostponeRepeatCount === 3
                audio: https://storage.yandexcloud.net/medicalcentre/operator_hangup.wav
                go!: /OperatorHangup
        else:
            script:
                log("Повтор после приветствия")
                if (!$session.RepeatCount) {
                    $session.RepeatCount = 1;
                }
                else {
                    $session.RepeatCount += 1;
                };
                if ($session.RepeatCount === 1) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_repeat_1_1.wav")
                    $reactions.audio($session.app);
                    $reactions.audio($session.spec);
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_repeat_1_2.wav")
                };
                if ($session.RepeatCount === 2) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_repeat_2_1.wav")
                    $reactions.audio($session.app);
                    $reactions.audio($session.spec);
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_repeat_2_2.wav")
                };
                if ($session.RepeatCount === 3) {
                    $reactions.transition({value: '/OperatorHangup', deferred: false});
                };
            
    state: WhatName
        intent!: /bot_name
        if: $session.path.indexOf( 'postpone' ) != -1
            script:
                log("Вопрос об имени бота")
                if (!$session.PostponeWhatNameCount) {
                    $session.PostponeWhatNameCount = 1;
                }
                else {
                    $session.PostponeWhatNameCount += 1;
                };
            if: $session.PostponeWhatNameCount === 1
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_bot_name.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
                
            elseif: $session.PostponeWhatNameCount === 2
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_company_1.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
            
            elseif: $session.PostponeWhatNameCount === 3 
                go!: /OperatorHangup
               
        else:
            script:
                log("Вопрос об имени бота")
                if (!$session.WhatNameCountCount) {
                    $session.WhatNameCountCount = 1;
                }
                else {
                    $session.WhatNameCountCount += 1;
                };
                if ($session.WhatNameCountCount === 1) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_bot_name.wav")
                };
                if ($session.WhatNameCountCount === 2) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_company_1.wav")
                };
                if ($session.WhatNameCountCount === 3) {
                    $reactions.transition({value: '/OperatorHangup', deferred: false});
                };
        
    state: CallGoal
        intent!: /call_goal
        if: $session.path.indexOf( 'postpone' ) != -1
            script:
                log("Вопрос о цели звонка")
                if (!$session.PostponeCallGoalCount) {
                    $session.PostponeCallGoalCount = 1;
                }
                else {
                    $session.PostponeCallGoalCount += 1;
                };
            if: $session.PostponeCallGoalCount === 1
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_call_goal.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
            
            elseif: $session.PostponeCallGoalCount === 2
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_repeat_1.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
            
            elseif: $session.PostponeCallGoalCount === 3
                audio: https://storage.yandexcloud.net/medicalcentre/operator_hangup.wav
                go!: /OperatorHangup
        else:
            script:
                log("Вопрос о цели звонка")
                if (!$session.CallGoalCount) {
                    $session.CallGoalCount = 1;
                }
                else {
                    $session.CallGoalCount += 1;
                };
                if ($session.CallGoalCount === 1) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_call_goal.wav")
                };
                if ($session.CallGoalCount === 2) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_repeat_1_1.wav")
                    $reactions.audio($session.app);
                    $reactions.audio($session.spec);
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_repeat_2_2.wav")
                };
                if ($session.CallGoalCount === 3) {
                    $reactions.transition({value: '/OperatorHangup', deferred: false})
                };
        
    state: Robot
        intent!: /robot
        if: $session.path.indexOf( 'postpone' ) != -1
            script:
                log("Распознан робот на переносе")
                if (!$session.PostponeRobotCount) {
                    $session.PostponeRobotCount = 1;
                }
                else {
                    $session.PostponeRobotCount += 1;
                };
                if ($session.PostponeRobotCount === 1) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_robot_1.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/11_12.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/13_14.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/14_15.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_tail_1.wav");
                };
                if ($session.PostponeRobotCount === 2) { 
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_robot_2.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/11_12.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/13_14.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/14_15.wav");
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_tail_2.wav");
                };
                if ($session.PostponeRobotCount === 3) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/negative_hangup.wav')
                    log("Negative hangup");
                    $dialer.setCallResult("Negative hangup");
                    $analytics.setSessionResult("Negative hangup");
                    $dialer.hangUp();
                    $jsapi.stopSession();
                };
        else:
            script:
                log('Распознан робот после приветствия')
                if (!$session.RobotCount) {
                    $session.RobotCount = 1;
                }
                else {
                    $session.RobotCount += 1;
                };
                if ($session.RobotCount === 1) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/hello_robot_1.wav')
                };
                if ($session.RobotCount  === 2) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/hello_robot_2.wav');
                };
                if ($session.RobotCount  === 3) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/negative_hangup.wav");
                    log("Negative hangup");
                    $dialer.setCallResult("Negative hangup");
                    $analytics.setSessionResult("Negative hangup");
                    $dialer.hangUp();
                    $jsapi.stopSession();
                };
            
    state: DontDisturb
        intent!: /dont_disturb
        if: $session.path.indexOf( 'postpone' ) != -1
            script:
                log("Неявный негатив")
                if (!$session.PostponeDontDisturbCount) {
                    $session.PostponeDontDisturbCount = 1;
                }
                else {
                    $session.PostponeDontDisturbCount += 1;
                };
            if: $session.PostponeDontDisturbCount === 1
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_dont_disturb.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
            
            elseif: $session.PostponeDontDisturbCount === 2
                audio: https://storage.yandexcloud.net/medicalcentre/dont_disturb_hangup.wav
                script:
                    log("Don't disturb hangup");
                    $dialer.setCallResult("Don't disturb hangup");
                    $analytics.setSessionResult("Don't disturb hangup");
                    $dialer.hangUp();
                    $jsapi.stopSession();
        else:
            script:
                log("Неявный негатив. Не беспокоить")
                if (!$session.DontDisturbCount) {
                    $session.DontDisturbCount = 1;
                }
                else {
                    $session.DontDisturbCount += 1;
                };
                if ($session.DontDisturbCount === 1) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_dont_disturb.wav")
                };
                if ($session.DontDisturbCount === 2) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/dont_disturb_hangup.wav")
                    log("Don't disturb hangup");
                    $dialer.setCallResult("Don't disturb hangup");
                    $analytics.setSessionResult("Don't disturb hangup");
                    $dialer.hangUp();
                    $jsapi.stopSession();
                };
            
    state: PhoneNumber
        intent!: /phone_number
        if: $session.path.indexOf( 'postpone' ) != -1
            script:
                log('Вопрос откуда номер')
                if (!$session.PostponePhoneNumberCount) {
                    $session.PostponePhoneNumberCount = 1;
                }
                else {
                    $session.PostponePhoneNumberCount += 1;
                };
            if: $session.PostponePhoneNumberCount === 1
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_number.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
            elseif: $session.PostponePhoneNumberCount === 2
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_company_1.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
            elseif: $session.PostponePhoneNumberCount === 3
                audio: https://storage.yandexcloud.net/medicalcentre/operator_hangup.wav
                go!: /OperatorHangup
        else:
            script:
                log('Вопрос откуда номер')
                if (!$session.PhoneNumberCount) {
                    $session.PhoneNumberCount = 1;
                }
                else {
                    $session.PhoneNumberCount += 1;
                };
                if ($session.PhoneNumberCount === 1) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_number_1.wav");
                    $reactions.audio($session.app);
                    $reactions.audio($session.spec);
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_number_2.wav");
                };
                if ($session.PhoneNumberCount === 2) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_company_1.wav");
                };
                if ($session.PhoneNumberCount === 3) {
                    $reactions.transition({value: '/OperatorHangup', deferred: false});
                };
            
    state: WhatDate 
        intent!: /whatdate
        if: $session.path.indexOf( 'postpone' ) != -1 
            script:
                log("Вопросы про дату приема")
                if (!$session.PostponeDateCount) {
                    $session.PostponeDateCount = 1;
                }
                else {
                    $session.PostponeDateCount += 1;
                };
            if: $session.PostponeDateCount === 1
               audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_acc.wav
               audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
               audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_acc.wav
               audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
               audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_acc.wav
               audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
               audio: https://storage.yandexcloud.net/medicalcentre/postpone_date.wav
               go!: /PostponeTail
            
            elseif: $session.PostponeDateCount === 2
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_default_1.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/18_05_23_acc.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/11_12.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/20_05_23_acc.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/13_14.wav
                audio: https://storage.yandexcloud.net/medicalcentre/day/03_06_23_acc.wav
                audio: https://storage.yandexcloud.net/medicalcentre/time/14_15.wav
                go!: /PostponeTail
            
            elseif: $session.PostponeDateCount === 3
                audio: https://storage.yandexcloud.net/medicalcentre/operator_hangup.wav
                go!: /OperatorHangup
        else:    
            script:
                log("Вопросы про дату приема")
                if (!$session.WhatDateCount) {
                    $session.WhatDateCount = 1;
                    }
                else {
                    $session.WhatDateCount += 1;
                    };
                if ($session.WhatDateCount === 1) {
                    $reactions.audio($session.app);
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_date.wav");
                };
                if ($session.WhatDateCount === 2) {
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/hello_default_1_1.wav');
                    $reactions.audio($session.app);
                    $reactions.audio($session.spec);
                    $reactions.audio('https://storage.yandexcloud.net/medicalcentre/hello_default_1_2.wav');
                };
                if ($session.WhatDateCount === 3) {
                    $reactions.transition({value: '/OperatorHangup', deferred: false});
                };
                
    state: WhatDoctor
        intent!: /whatdoctor
        if: $session.path.indexOf( 'postpone' ) != -1
            script:
                log('Вопрос про доктора')
                if (!$session.WhatDoctorPostponeCount) {
                    $session.WhatDoctorPostponeCount = 1;
                }
                else {
                    $session.WhatDoctorPostponeCount += 1;
                };
            if: $session.WhatDoctorPostponeCount === 3
                go!: /OperatorHangup
            else:
                audio: https://storage.yandexcloud.net/medicalcentre/postpone_what_doctor.wav
                audio: {{$session.spec}}
                audio: {{$session.name}}
                go!: /PostponeTail
        else:
            script:
                log('Вопрос про доктора')
                if (!$session.WhatDoctorCount) {
                    $session.WhatDoctorCount = 1;
                    }
                else {
                    $session.WhatDoctorCount += 1;
                    };
                if ($session.WhatDoctorCount === 3) {
                    $reactions.transition({value: '/OperatorHangup', deferred: false});
                }
                else {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_what_doctor_1.wav");
                    $reactions.audio($session.spec);
                    $reactions.audio($session.name);
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_what_doctor_2.wav");
                };
    
    state: WontCome
        intent!: /wontcome
        if: $session.path.indexOf( 'postpone' ) != -1
            script:
                log("Отказ от переноса")
            audio: https://storage.yandexcloud.net/medicalcentre/postpone_wont_come.wav
            audio: https://storage.yandexcloud.net/medicalcentre/negative_hangup.wav
            script:
                $dialer.setCallResult("Отказ от переноса");
                $analytics.setSessionResult("Отказ от переноса");
                $dialer.hangUp();
                $jsapi.stopSession();
        else:
            script:
                log("Не придёт на приём")
                if (!$session.WontComeCount) {
                    $session.WontComeCount = 1;
                }
                else {
                    $session.WontComeCount += 1;
                };
                if ($session.WontComeCount === 1) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_wont_come.wav");
                };
                if ($session.WontComeCount === 2) {
                    $reactions.audio("https://storage.yandexcloud.net/medicalcentre/negative_hangup.wav");
                    $dialer.setCallResult("Не придёт на приём");
                    $analytics.setSessionResult("Не придёт на приём");
                    $dialer.hangUp();
                    $jsapi.stopSession();
                };
    
    state: Cancel
        intent!: /cancel
        script:
            log("Отмена записи")
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/hello_cancel.wav");
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/negative_hangup.wav");
            $dialer.setCallResult("Отмена записи");
            $analytics.setSessionResult("Отмена записи");
            $dialer.hangUp();
            $jsapi.stopSession();
    
    state: Postpone
        intent!: /postpone
        script:
            $session.path.push('postpone')
            log("Перенос записи")
        go!: /ChooseDate
    
    state: Call_back
        intent!: /call_back
        intent!: /busy
        script:
            log("Неудобно говорить после приветствия")
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/callback_hangup.wav");
            $dialer.setCallResult("Неудобно говорить после приветствия");
            $analytics.setSessionResult("Неудобно говорить после приветствия");
            $dialer.hangUp();
            $jsapi.stopSession();
    
    state: Confirm
        intent!: /confirmation
        script:
            log("Придёт на приём")
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/positive_hangup.wav")
            $dialer.setCallResult("Придёт на приём");
            $analytics.setSessionResult("Придёт на приём");
            $dialer.hangUp();
            $jsapi.stopSession();
            
    state: NotService
        intent!: /not_service
        script:
            log("Перевод на оператора")
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/operator_hangup.wav")
            $dialer.setCallResult("Перевод на оператора");
            $analytics.setSessionResult("Перевод на оператора");
            $dialer.hangUp();
            $jsapi.stopSession();
            
    state: ChooseDate
        script:
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_main.wav");
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/18_05_23_acc.wav");
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/11_12.wav");
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/20_05_23_acc.wav");
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/13_14.wav");
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/03_06_23_acc.wav");
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/14_15.wav");
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_tail_main.wav");
        go: /NewDate
        
    state: NewDate
        intent: /newdate
        script:
            log("Перенос на новый слот")
            if ($parseTree._newdate){
                if ($parseTree._newdate === 'восемнадцатое') {
                    $temp.date = 18
                };
                if ($parseTree._newdate === 'двадцатое') {
                    $temp.date = 20
                };
                if ($parseTree._newdate === 'третье') {
                    $temp.date = 3
                };
            }
            else {
                if ($parseTree._date.day === 18 && $parseTree._date.month === 5 || $parseTree._number === 18) {
                    $temp.date = 18
                };
                if ($parseTree._date.day === 20 && $parseTree._date.month === 5 || $parseTree._number === 20) {
                    $temp.date = 20
                };
                if ($parseTree._date.day === 3 && $parseTree._date.month === 6 || $parseTree._number === 3) {
                    $temp.date = 3
                };
            };
            if ($temp.date === 18){
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_new_date.wav");
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/18_05_23_nom.wav");
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/11_12.wav");
                };
            if ($temp.date === 20){
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_new_date.wav");
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/20_05_23_nom.wav");
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/13_14.wav");
                };
            if ($temp.date === 3){
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_new_date.wav");
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/day/03_06_23_nom.wav");
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/time/14_15.wav");
                };
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_hangup.wav");
            $dialer.setCallResult("Перенос на новый слот");
            $analytics.setSessionResult("Перенос на новый слот");
            $dialer.hangUp();
            $jsapi.stopSession();
            
    state: PostponeTail || noContext = true
        script:
            if (!$session.PostponeTailCount || $session.PostponeTailCount === 4) {
                $session.PostponeTailCount = 1;
                }
            else {
                $session.PostponeTailCount += 1;
                };
            if ($session.PostponeTailCount === 1) {
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_tail_1.wav");
            };
            if ($session.PostponeTailCount === 2) {
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_tail_2.wav");
            };
            if ($session.PostponeTailCount === 3) {
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_tail_3.wav");
            };
            if ($session.PostponeTailCount === 4) {
                $reactions.audio("https://storage.yandexcloud.net/medicalcentre/postpone_tail_4.wav");
            };
    
    state: BadSlots
        intent!: /badslots
        script:
            log("Неудобные слоты для переноса записи")
            $reactions.audio("https://storage.yandexcloud.net/medicalcentre/operator_hangup.wav");
            $dialer.setCallResult("Неудобные слоты для переноса записи");
            $analytics.setSessionResult("Неудобные слоты для переноса записи");
            $dialer.hangUp();
            $jsapi.stopSession();
    
    state: OperatorHangup
        script:
            $reactions.audio('https://storage.yandexcloud.net/medicalcentre/operator_hangup.wav');
            log("вопросы/возражения => перевод на оператора");
            $dialer.setCallResult("вопросы/возражения => перевод на оператора");
            $analytics.setSessionResult("вопросы/возражения => перевод на оператора");
            $dialer.hangUp();
            $jsapi.stopSession();
    