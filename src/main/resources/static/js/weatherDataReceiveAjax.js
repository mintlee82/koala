function weatherDataReceive() {
	let nowDt = new Date();
	let nowTm = nowDt.getHours()+'00';
	nowDt.setHours(nowDt.getHours() - 1);
	let baseDt = nowDt.getFullYear()+pad(nowDt.getMonth()+1)+pad(nowDt.getDate());
	let baseTm = nowDt.getHours()+'30';

    var messageDTO={
        serviceKey:"q6DZDzbe0BITlR5wUcAmeUMtLO0MUV39ROlCBqxg1LhCwMPLKU/sBPgFpSfwv4js+OyFBsyjPKT0/wPfjPtmTg=="
       ,dataType:"JSON"
       ,base_date:baseDt
       ,base_time:baseTm
       ,nx:"61"
       ,ny:"129"
       ,numOfRows:60
    };

    $.ajax({
        url: "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst",
        data: messageDTO,
        type:"GET",
        dataType:"JSON"
    }).done(function(json){
		callbackWeather(json, baseDt, nowTm);
	});
}

/**
 * 
 */
function callbackWeather(json, baseDt, nowTm){
	let lT1H = "";
	let lSKY = "";
	let lPTY = "";
	console.log(json.response.body.items);
	if(json.response.body.items.item != null){
		console.log(json.response.body.items.item.length);
		for(let i=0; i <json.response.body.items.item.length; i++){
			if(json.response.body.items.item[i].fcstDate == baseDt && json.response.body.items.item[i].fcstTime == nowTm) {
				console.log(json.response.body.items.item[i]);
				if(json.response.body.items.item[i].category == "T1H"){
					lT1H = json.response.body.items.item[i].fcstValue;
				}
				
				if(json.response.body.items.item[i].category == "SKY"){
					lSKY = json.response.body.items.item[i].fcstValue;
				}
				
				if(json.response.body.items.item[i].category == "PTY"){
					lPTY = json.response.body.items.item[i].fcstValue;
				}
			}				
		}
	}
	
	let weather = "";
	if(lSKY == "1"){
		weather = "맑음";
	}else if(lSKY == "3"){
		weather = "구름많음";		
	}else if(lSKY == "4"){
		weather = "흐림";		
	}else{
		if(lPTY == "1"){
			weather = "비";
		}else if(lPTY == "2"){
			weather = "비/눈";
		}else if(lPTY == "3"){
			weather = "눈";
		}else if(lPTY == "5"){
			weather = "빗방울";
		}else if(lPTY == "6"){
			weather = "빗방울눈날림";
		}else if(lPTY == "7"){
			weather = "눈날림";
		}
	}
	
	
    $("<h1>").text("날씨").appendTo("body");
	$("<h3>").text(lT1H+" °C" + " (" + weather + ")").appendTo("body");
}

function pad(n){
	return n<10 ? '0'+n : ''+n;
}