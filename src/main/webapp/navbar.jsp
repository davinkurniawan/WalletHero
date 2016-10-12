
        <nav class="navbar transparent navbar-custom navbar-fixed-top" style="" role="navigation">
          <div class="container navbar-border">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="${applicationScope['ROUTER_PUBLIC']}"><b>WalletHero</b></a>
            </div>
            <div class="navbar-collapse collapse">
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

		