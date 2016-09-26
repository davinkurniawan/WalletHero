<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Money Test</title>
	
	<%@ include file="bootstrapHeader.jsp" %>
</head>
<body>
	Money Test! which is run by the MoneyTest.jsp Page!
	<br />
	<br />
	<br />
	<br />
	
	<form action="#" onSubmit="return onClickGetRate(this)">	
		<label>
			From:
			<select id="from_currency" name="from_currency">
				<option value="DZD">Algeria Dinars 					- DZD</option>
				<option value="ARS">Argentina Pesos 				- ARS</option>
				<option value="AUD">Australia Dollars 				- AUD</option>
				<option value="BHD">Bahrain Dinars 					- BHD</option>
				<option value="BRL">Brazil Reais 					- BRL</option>
				<option value="BGN">Bulgaria Leva 					- BGN</option>
				<option value="CAD">Canada Dollars 					- CAD</option>
				<option value="CLP">Chile Pesos 					- CLP</option>
				<option value="CNY">China Yuan Renminbi (RMB) 		- CNY</option>
				<option value="COP">Colombia Pesos 					- COP</option>
				<option value="CRC">Costa Rica Colones 				- CRC</option>
				<option value="HRK">Croatia Kuna 					- HRK</option>
				<option value="CZK">Czech Republic Koruny 			- CZK</option>
				<option value="DKK">Denmark Kroner 					- DKK</option>
				<option value="DOP">Dominican Republic Pesos 		- DOP</option>
				<option value="EGP">Egypt Pounds 					- EGP</option>
				<option value="EEK">Estonia Krooni 					- EEK</option>
				<option value="EUR">Euro 							- EUR</option>
				<option value="FJD">Fiji Dollars 					- FJD</option>
				<option value="HKD">Hong Kong Dollars 				- HKD</option>
				<option value="HUF">Hungary Forint 					- HUF</option>
				<option value="ISK">Iceland Kronur 					- ISK</option>
				<option value="INR">India Rupees 					- INR</option>
				<option value="IDR">Indonesia Rupiahs 				- IDR</option>
				<option value="ILS">Israel New Shekels 				- ILS</option>
				<option value="JMD">Jamaica Dollars 				- JMD</option>
				<option value="JPY">Japan Yen 						- JPY</option>
				<option value="JOD">Jordan Dinars 					- JOD</option>
				<option value="KES">Kenya Shillings 				- KES</option>
				<option value="KWD">Kuwait Dinars 					- KWD</option>
				<option value="LBP">Lebanon Pounds 					- LBP</option>
				<option value="MYR">Malaysia Ringgits 				- MYR</option>
				<option value="MUR">Mauritius Rupees 				- MUR</option>
				<option value="MXN">Mexico Pesos 					- MXN</option>
				<option value="MAD">Morocco Dirhams 				- MAD</option>
				<option value="NZD">New Zealand Dollars 			- NZD</option>
				<option value="NOK">Norway Kroner 					- NOK</option>
				<option value="OMR">Oman Rials 						- OMR</option>
				<option value="PKR">Pakistan Rupees 				- PKR</option>
				<option value="PEN">Peru Nuevos Soles 				- PEN</option>
				<option value="PHP">Philippines Pesos 				- PHP</option>
				<option value="PLN">Poland Zlotych 					- PLN</option>
				<option value="QAR">Qatar Riyals 					- QAR</option>
				<option value="RON">Romania New Lei 				- RON</option>
				<option value="RUB">Russia Rubles 					- RUB</option>
				<option value="SAR">Saudi Arabia Riyals 			- SAR</option>
				<option value="SGD">Singapore Dollars 				- SGD</option>
				<option value="SKK">Slovakia Koruny 				- SKK</option>
				<option value="ZAR">South Africa Rand 				- ZAR</option>
				<option value="KRW">South Korea Won 				- KRW</option>
				<option value="LKR">Sri Lanka Rupees 				- LKR</option>
				<option value="SEK">Sweden Kronor 					- SEK</option>
				<option value="CHF">Switzerland Francs 				- CHF</option>
				<option value="TWD">Taiwan New Dollars 				- TWD</option>
				<option value="THB">Thailand Baht 					- THB</option>
				<option value="TTD">Trinidad and Tobago Dollars 	- TTD</option>
				<option value="TND">Tunisia Dinars 					- TND</option>
				<option value="TRY">Turkey Lira 					- TRY</option>
				<option value="AED">United Arab Emirates Dirhams 	- AED</option>
				<option value="GBP">United Kingdom Pounds 			- GBP</option>
				<option value="USD">United States Dollars 			- USD</option>
				<option value="VEB">Venezuela Bolivares 			- VEB</option>
				<option value="VND">Vietnam Dong 					- VND</option>
				<option value="ZMK">Zambia Kwacha 					- ZMK</option>
			</select>
		</label>
		<br/>
		<input type="text" name="from_amount" id="from_amount" placeholder="From..."></input>
		<br/><br/>
		<label>
			To:
			<select id="to_currency" name="to_currency">
				<option value="DZD">Algeria Dinars 					- DZD</option>
				<option value="ARS">Argentina Pesos 				- ARS</option>
				<option value="AUD">Australia Dollars 				- AUD</option>
				<option value="BHD">Bahrain Dinars 					- BHD</option>
				<option value="BRL">Brazil Reais 					- BRL</option>
				<option value="BGN">Bulgaria Leva 					- BGN</option>
				<option value="CAD">Canada Dollars 					- CAD</option>
				<option value="CLP">Chile Pesos 					- CLP</option>
				<option value="CNY">China Yuan Renminbi (RMB) 		- CNY</option>
				<option value="COP">Colombia Pesos 					- COP</option>
				<option value="CRC">Costa Rica Colones 				- CRC</option>
				<option value="HRK">Croatia Kuna 					- HRK</option>
				<option value="CZK">Czech Republic Koruny 			- CZK</option>
				<option value="DKK">Denmark Kroner 					- DKK</option>
				<option value="DOP">Dominican Republic Pesos 		- DOP</option>
				<option value="EGP">Egypt Pounds 					- EGP</option>
				<option value="EEK">Estonia Krooni 					- EEK</option>
				<option value="EUR">Euro 							- EUR</option>
				<option value="FJD">Fiji Dollars 					- FJD</option>
				<option value="HKD">Hong Kong Dollars 				- HKD</option>
				<option value="HUF">Hungary Forint 					- HUF</option>
				<option value="ISK">Iceland Kronur 					- ISK</option>
				<option value="INR">India Rupees 					- INR</option>
				<option value="IDR">Indonesia Rupiahs 				- IDR</option>
				<option value="ILS">Israel New Shekels 				- ILS</option>
				<option value="JMD">Jamaica Dollars 				- JMD</option>
				<option value="JPY">Japan Yen 						- JPY</option>
				<option value="JOD">Jordan Dinars 					- JOD</option>
				<option value="KES">Kenya Shillings 				- KES</option>
				<option value="KWD">Kuwait Dinars 					- KWD</option>
				<option value="LBP">Lebanon Pounds 					- LBP</option>
				<option value="MYR">Malaysia Ringgits 				- MYR</option>
				<option value="MUR">Mauritius Rupees 				- MUR</option>
				<option value="MXN">Mexico Pesos 					- MXN</option>
				<option value="MAD">Morocco Dirhams 				- MAD</option>
				<option value="NZD">New Zealand Dollars 			- NZD</option>
				<option value="NOK">Norway Kroner 					- NOK</option>
				<option value="OMR">Oman Rials 						- OMR</option>
				<option value="PKR">Pakistan Rupees 				- PKR</option>
				<option value="PEN">Peru Nuevos Soles 				- PEN</option>
				<option value="PHP">Philippines Pesos 				- PHP</option>
				<option value="PLN">Poland Zlotych 					- PLN</option>
				<option value="QAR">Qatar Riyals 					- QAR</option>
				<option value="RON">Romania New Lei 				- RON</option>
				<option value="RUB">Russia Rubles 					- RUB</option>
				<option value="SAR">Saudi Arabia Riyals 			- SAR</option>
				<option value="SGD">Singapore Dollars 				- SGD</option>
				<option value="SKK">Slovakia Koruny 				- SKK</option>
				<option value="ZAR">South Africa Rand 				- ZAR</option>
				<option value="KRW">South Korea Won 				- KRW</option>
				<option value="LKR">Sri Lanka Rupees 				- LKR</option>
				<option value="SEK">Sweden Kronor 					- SEK</option>
				<option value="CHF">Switzerland Francs 				- CHF</option>
				<option value="TWD">Taiwan New Dollars 				- TWD</option>
				<option value="THB">Thailand Baht 					- THB</option>
				<option value="TTD">Trinidad and Tobago Dollars 	- TTD</option>
				<option value="TND">Tunisia Dinars 					- TND</option>
				<option value="TRY">Turkey Lira 					- TRY</option>
				<option value="AED">United Arab Emirates Dirhams 	- AED</option>
				<option value="GBP">United Kingdom Pounds 			- GBP</option>
				<option value="USD">United States Dollars 			- USD</option>
				<option value="VEB">Venezuela Bolivares 			- VEB</option>
				<option value="VND">Vietnam Dong 					- VND</option>
				<option value="ZMK">Zambia Kwacha 					- ZMK</option>
			</select>
		</label>
		<br/>
		<input type="text" name="to_amount" id="to_amount" placeholder="To..."></input>
		<br/><br/>
		<input type="submit"/>
	 </form>
	 
	 <script type="text/javascript">
	 	function onClickGetRate(form){
	 		var from = document.getElementById("from_currency");
	 		var selected_from = from.options[from.selectedIndex].value;
	 		
	 		var to = document.getElementById("to_currency");
	 		var selected_to = to.options[to.selectedIndex].value;
	 		
	 		getRate(selected_from, selected_to);
	 		
	 		return false;
	 	}
	 
		function getRate(from, to) {
			var script = document.createElement('script');
			script.setAttribute('src', "http://query.yahooapis.com/v1/public/yql?q=select%20rate%2Cname%20from%20csv%20where%20url%3D'http%3A%2F%2Fdownload.finance.yahoo.com%2Fd%2Fquotes%3Fs%3D"+ from + to +"%253DX%26f%3Dl1n'%20and%20columns%3D'rate%2Cname'&format=json&callback=parseExchangeRate");
			document.body.appendChild(script);
		}
		
		function parseExchangeRate(data) {
			var name = data.query.results.row.name;
			var rate = parseFloat(data.query.results.row.rate, 10);
			
			var amount_from = document.getElementById("from_amount").value.trim();
			document.getElementById("to_amount").value = rate * amount_from;
			
			//alert("Exchange rate " + name + " is " + rate);
		}

		//getRate("SEK", "USD");
		//getRate("USD", "SEK");
	</script>
</body>
</html>