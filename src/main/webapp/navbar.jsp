<!-- <nav class="navbar navbar-custom" role="navigation">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="javascript:void(0)">Brand</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="javascript:void(0)">Link</a></li>
					<li><a href="javascript:void(0)">Link</a></li>
				</ul>
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="javascript:void(0)">Link</a></li>
				</ul>
			</div>
		</nav> -->
		
		<div class="navbar-wrapper">
      <div class="container">

        <nav class="navbar navbar-custom navbar-static-top">
          <div class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="${applicationScope['ROUTER_PUBLIC']}">WalletHero</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li class="${public_home }" ><a href="${applicationScope['ROUTER_PUBLIC']}">Home</a></li>
                <li class="${signIn }"><a href="${applicationScope['ROUTER_SIGNIN']}">Sign In</a></li>
                <li class="${signUp }"><a href="${applicationScope['ROUTER_SIGNUP']}">Sign Up</a></li>
                <li class="dropdown ${tools }">
                	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Tools <span class="caret"></span></a>
                	<ul class="dropdown-menu" aria-labelledby="tools">
                		<li class="${currencyConverter }"><a href="${applicationScope['ROUTER_CURRENCYCONVERTER']}">Currency Converter</a></li>
               		</ul>
                </li>
                <li class="${about }"><a href="${applicationScope['ROUTER_ABOUT']}">About</a></li>
              </ul>
            </div>
          </div>
        </nav>
      </div>
    </div>
		