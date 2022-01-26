const toggledMenu = document.querySelector(".toggled-menu");
const toggler = document.querySelector(".toggler");
let toggled = false;




function togglerFunc() {
  let is = toggled;
  let icon = toggled
    ? `<i class="fa fa-bars"></i>`
    : `<i class="fa fa-close" style="position:relative;z-index: 9999!important;"></i>`;
  toggler.innerHTML = icon;
  toggledMenu.classList.toggle("active");
  toggled = !is;
}



var swiper = new Swiper(".swiper-container", {
  slidesPerView: 3,
  slidesPerGroup: 1,
  loop: false,
  initialSlide: 2,
  loopFillGroupWithBlank: true,
  pagination: {
    el: ".swiper-pagination",
    clickable: true,
  },
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
  breakpoints: {
    // when window width is >= 320px
    0: {
      slidesPerView: 3,
      spaceBetween: 5,
    },
    // when window width is >= 480px
    500: {
      slidesPerView: 3,
      spaceBetween: 10,
    },
    // when window width is >= 640px
    840: {
      slidesPerView: 4,
      spaceBetween: 20,
    },
    1050: {
      slidesPerView: 5,
      spaceBetween: 20,
    },
    640: {
      slidesPerView: 3,
      spaceBetween: 20,
    },
    1300: {
      slidesPerView: 6,
      spaceBetween: 20,
      pagination: {
        el: ".swiper-paginatin",
        clickable: true,
        type: "custom",
      },
    },
  },
});
var $body = $("body");
var $background = $(".background-overlay");
var $menu = $(".static-menu-wrapper");
var $menuItems = $(document).find(".menu-item-static");
var $searchWrapper = $(".search-wrapper");
var $searchFormWrapper = $(".search-form-wrapper");
var $searchSuggestionList = $(".search-suggestions-list");
var $searchSuggestionItems = $searchSuggestionList.children("li");

function displaySearch() {
  if (!$body.hasClass("search-on")) {
    $body.addClass("search-on");
    // Fade out the menu items
    $menu.velocity(
      {
        opacity: 0,
      },
      {
        duration: 195,
        easing: [20],
      }
    );
    $menuItems.velocity(
      {
        opacity: 0,
        scale: 0.7,
      },
      {
        duration: 210,
        easing: [20],
      }
    );
    // Display background overlay
    $background.velocity(
      {
        opacity: 1,
      },
      {
        duration: 50,
        easing: "easeInQuad",
        display: "block",
      }
    );
    // Display the search
    $searchWrapper.addClass("search-displayed");
    $searchWrapper.velocity(
      {
        opacity: 1,
      },
      {
        duration: 200,
      }
    );
    $searchFormWrapper.velocity(
      {
        left: "10px",
        opacity: 1,
      },
      {
        duration: 600,
        easing: "easeOutSine",
        delay: 175,
      }
    );
    $searchSuggestionItems.velocity("transition.expandIn", {
      duration: 200,
      stagger: 25,
    });
    // Change search icon to x
    $("#search").html('<i class="fa fa-close"></i>');
  } else {
    $body.removeClass("search-on");
    $searchWrapper.removeClass("search-displayed");
    $menu.velocity("reverse");
    $menuItems.velocity(
      { opacity: 1, scale: 1 },
      {
        duration: 200,
        easing: [20],
        stagger: 100,
      }
    );
    $background.velocity("reverse");
    $searchFormWrapper.velocity("reverse");
    $searchWrapper.add($searchSuggestionItems).velocity("reverse");
    $("#search").html('<i class="fa fa-search"></i>');
  }
}



function fixModal(modalId){
    document.body.append($(modalId)[0]);
}

$("#search").on("click", function () {
  displaySearch();
});

$('#button1').click(() => {
  $('#modal1').css('display', 'none');
  $('#modal2').css('display', 'initial');
})
$('#button2').click(() => {
  $('#modal2').css('display', 'none');
  $('#modal3').css('display', 'initial');
})
$('#button3').click(() => {
  $('#modal3').css('display', 'none');
})