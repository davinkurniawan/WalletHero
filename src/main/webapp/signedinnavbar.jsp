
       <nav class="navbar transparent navbar-custom navbar-fixed-top" style="" role="navigation">
          <div class="container navbar-border">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="${applicationScope['ROUTER_HOME']}"><b>WalletHero</b></a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li class="${home }"><a href="${applicationScope['ROUTER_HOME']}">Home</a></li>
                
                <li class="dropdown ${transactions }">
                	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Transactions <span class="caret"></span></a>
                	<ul class="dropdown-menu" aria-labelledby="transactions">
 						<li class="${viewTransactions }"><a href="${applicationScope['ROUTER_VIEWTRANSACTIONS']}">View Transactions</a></li>
                		<li role="separator" class="divider"></li>
                		<li class="${addTransaction }"><a href="${applicationScope['ROUTER_ADDTRANSACTION']}">Add a Transaction</a></li>               		
                	</ul>
                </li>
                
                <li class="dropdown ${goals }">
                	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Goals <span class="caret"></span></a>
                	<ul class="dropdown-menu" aria-labelledby="goals">
 						<!-- <li class="${viewGoals }"><a href="${applicationScope['ROUTER_VIEWGOALS']}">View Goals</a></li>  -->
                		<li class="${viewGoals }"><a href="${applicationScope['ROUTER_VIEWGOALS']}">View Goals</a></li> 
                		<li role="separator" class="divider"></li>
                		<li class="${addGoal }"><a href="${applicationScope['ROUTER_ADDGOAL']}">Add a Goal</a></li>
                	</ul>
                </li>
              
				<li class="${deals }"><a href="${applicationScope['ROUTER_DEALS']}">Deals</a></li>
				
				<li class="dropdown ${tools }">
                	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Tools <span class="caret"></span></a>
                	<ul class="dropdown-menu" aria-labelledby="tools">
                		<li class="${currencyConverter }"><a href="${applicationScope['ROUTER_CURRENCYCONVERTER']}">Currency Converter</a></li>
               		</ul>
                </li>      

                <li class="${profile }"><a href="${applicationScope['ROUTER_PROFILE']}">Profile</a></li>
                <li class="${help }"><a href="${applicationScope['ROUTER_HELP']}">Help</a></li>
                <li class="${about }"><a href="${applicationScope['ROUTER_ABOUT']}">About</a></li>
				<li class="${signOut }"><a href="${applicationScope['ROUTER_SIGNOUT']}" onclick="return confirm('Are you sure you want to Sign Out?');">Sign Out</a></li>
              </ul>
            </div>
          </div>
        </nav>
