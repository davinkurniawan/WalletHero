<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <li class="${home }"><a href="${applicationScope['ROUTER_HOME']}">Home</a></li>
                
                <li class="${viewTransactions }"><a href="${applicationScope['ROUTER_VIEWTRANSACTIONS']}">Transactions</a></li>
                <li class="${addTransaction }"><a href="${applicationScope['ROUTER_ADDTRANSACTION']}">Add a Transaction</a></li>
                <li class="${addGoal }"><a href="${applicationScope['ROUTER_ADDGOAL']}">Add a Goal</a></li>
                <li class="${currencyConverter }"><a href="${applicationScope['ROUTER_CURRENCYCONVERTER']}">Currency Converter</a></li>
                
                <li class="${profile }"><a href="${applicationScope['ROUTER_PROFILE']}">Profile</a></li>
                <li class="${about }"><a href="${applicationScope['ROUTER_ABOUT']}">About</a></li>
                <li class="${signOut }"><a href="${applicationScope['ROUTER_SIGNOUT']}" onclick="return confirm('Are you sure you want to Sign Out?');">Sign Out</a></li>

                <!--<li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li role="separator" class="divider"></li>
                    <li class="dropdown-header">Nav header</li>
                    <li><a href="#">Separated link</a></li>
                    <li><a href="#">One more separated link</a></li>
                  </ul>
                </li>-->

              </ul>
            </div>
          </div>
        </nav>
      </div>
    </div>