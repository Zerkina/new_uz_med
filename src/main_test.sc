require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        script: 
            $jsapi.startSession();
        #a: Введите номер вашей карты.
        audio: https://storage.yandexcloud.net/tigerbot/numbers/cart_number.wav
        go: /Start/Cart_number
        
        state: Cart_number
            intent: /number
            script: 
                $session.cart_number = ''
                for(var num = 0; num < $entities.length; num++){
                    $session.cart_number = $session.cart_number + $entities[num].value;
                };
                $session.cart_number_container = []
                
                for (var l = 0; l <  $session.cart_number.length; l++){
                    if($session.cart_number[l] === '0'){
                        $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/zero.wav"
                        $session.cart_number_container.push($session.link)
                    }
                    else {
                    $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/" +  $session.cart_number[l] + ".wav";
                    $session.cart_number_container.push($session.link)
                    };
                };
                
                $reactions.audio("https://storage.yandexcloud.net/tigerbot/numbers/cart_number_check.wav")
                
                for (var k = 0; k < $session.cart_number_container.length; k++){
                    $reactions.audio($session.cart_number_container[k])
                };
                
                $reactions.audio("https://storage.yandexcloud.net/tigerbot/numbers/is_it_right.wav")
           
        
        state: CheckState_YES
            intent!: /confirmation
            go!: /Start/Sum
        
        state: CheckState_NO
            intent!: /wontcome
            go!: /Start
        
        state: Sum
            script: 
                #var number = 100340
                var number = Math.floor(Math.random() * 999999999999)
                $session.number = number.toString().replace(/(\d)(?=(\d\d\d)+([^\d]|$))/g, '$1 ').split(' ');
                $session.num_container = [];
                for (var i = 0; i < $session.number.length; i++){
                    if($session.number[i].length === 3){
                        $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/" + $session.number[i][0] + "00" + ".wav";
                        $session.num_container.push($session.link);
                            
                        if($session.number[i][1] > 1){
                            $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/" + $session.number[i][1] + "0" + ".wav";
                            $session.num_container.push($session.link);
                            $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/" + $session.number[i][2] + ".wav";
                            $session.num_container.push($session.link);
                        };
                            
                        if($session.number[i][1] === 1){
                            $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/" + $session.number[i][1] + $session.number[i][2] + ".wav";
                            $session.num_container.push($session.link);
                        };
                            
                        if($session.number[i][1] === 0){
                            $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/" + $session.number[i][2] + ".wav";
                            $session.num_container.push($session.link);
                        };
                    };
                        
                    if($session.number[i].length === 2){
                        if($session.number[i][0] > 1){
                            $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/" + $session.number[i][0] + "0" + ".wav";
                            $session.num_container.push($session.link);
                            $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/" + $session.number[i][1] + ".wav";
                            $session.num_container.push($session.link);
                        };
                            
                        if($session.number[i][0] === 1){
                            $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/" + $session.number[i][0] + $session.number[i][1] + ".wav";
                            $session.num_container.push($session.link);
                        };
                    };
                        
                    if($session.number[i].length === 1){
                            if($session.number.length === 2){
                                $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/0" + $session.number[i][0] + ".wav";
                                $session.num_container.push($session.link);
                            }
                            else {
                            $session.link = "https://storage.yandexcloud.net/tigerbot/numbers/" + $session.number[i][0] + ".wav";
                            $session.num_container.push($session.link);
                            };
                    };
                    
                    $session.declention = $nlp.parseMorph($nlp.conform("рубль", $session.number[i]))['tags'];
                    if($session.declention.indexOf('nomn') != -1){
                            $session.tag = '_nom';
                        };
                    if($session.declention.indexOf('gent') != -1 && $session.declention.indexOf('sing') != -1){
                            $session.tag = '_2_4';
                        };
                    if($session.declention.indexOf('gent') != -1 && $session.declention.indexOf('plur') != -1){
                            $session.tag = '_5';
                        };
            
                    if($session.number.length === 4 && i === 0){
                            $session.link = 'https://storage.yandexcloud.net/tigerbot/numbers/1000000000' + $session.tag + '.wav';
                            $session.num_container.push($session.link);
                    };
                    if ($session.number.length === 3 && i === 0 || $session.number.length === 4 && i === 1){
                            $session.link = 'https://storage.yandexcloud.net/tigerbot/numbers/1000000' + $session.tag + '.wav';
                            $session.num_container.push($session.link);
                    };
                    if($session.number.length === 2 && i === 0 || $session.number.length === 3 && i === 1 || $session.number.length === 4 && i === 2){
                            $session.link = 'https://storage.yandexcloud.net/tigerbot/numbers/1000' + $session.tag + '.wav';
                            $session.num_container.push($session.link);
                    };
                    if($session.number.length === 1 && i === 0 || $session.number.length === 2 && i === 1 || $session.number.length === 3 && i === 2 || $session.number.length === 4 && i === 3){
                            $session.link = 'https://storage.yandexcloud.net/tigerbot/numbers/rubles' + $session.tag + '.wav';
                            $session.num_container.push($session.link);
                    };
                };
                
                $reactions.audio("https://storage.yandexcloud.net/tigerbot/numbers/sum.wav");
                for (var i = 0; i < $session.num_container.length; i++){
                    $reactions.audio($session.num_container[i]);
                };
                $dialer.hangUp();
                $jsapi.stopSession();
    
    state: NoMatch || noContext = true
        event!: noMatch
        a: Я не совсем вас поняла. Повторите, пожалуйста.

