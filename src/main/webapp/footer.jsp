
<div id="div-footer" class="">
	<hr class="featurette-divider">
	<footer>
		<p class="pull-right"><a href="#">Back to top</a></p>
		<div class="small-print" style="background-color:transparent">
			<div class="container">
	    		<p style="color:Black">
	    			Copyright &copy; 2016 WalletHero Team
	    		</p>
	    	</div>
	    </div>
	</footer>
</div>
<script>
$(document).ready(function() {
    $(".dropdown-toggle").dropdown();
});
</script>

<script>
      $(document).on('click', '.panel-heading span.clickable', function(e){
		    var $this = $(this);
			if(!$this.hasClass('panel-collapsed')) {
				$this.parents('.panel').find('.panel-body').slideUp();
				$this.addClass('panel-collapsed');
			} else {
				$this.parents('.panel').find('.panel-body').slideDown();
				$this.removeClass('panel-collapsed');
			}
		})
</script>