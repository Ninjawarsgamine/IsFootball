<template>
  <div class="footer-container position-relative bottom-0 bg-black pt-5 pb-3 text-white">
    <div class="container-xxl px-3">
      <div class="row g-4 flex-wrap justify-content-between">

        <div class="col-12 col-sm-6 col-md-4 col-lg-3 mt-3" v-for="(section,index) in infoLegalLinks" 
        :key="index">
          <h5 class="text-uppercase fw-bold mb-3 custom-font">{{section.title}}</h5>
          <div class="d-flex flex-column gap-2">
            <a :href="info.link" v-for="(info,i) in section.fields" :key="i">
              {{ info.label }}
            </a>
          </div>
        </div>
        <div class="col-12 col-md-4 col-lg-3 mt-3 d-flex flex-column align-items-center">
          <h5 class="text-uppercase fw-bold mb-3">{{followUsInfo.title}}</h5>
          <div class="d-flex gap-3 flex-wrap align-items-center">
            <a :href="svg.link" class="social-icon" target="_blank" rel="noopener"
            v-for="(svg,index) in followUsInfo.svgsInfo" :key="index">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 256 256" class="icon-sm" fill="white">
                <g transform="scale(10.66667,10.66667)">
                  <path :d="svg.path"/>
                </g>
              </svg>
            </a>
          </div>
        </div>
      </div>

      <hr class="my-4 text-white opacity-100">

      <div class="d-flex flex-column flex-md-row align-items-center justify-content-center gap-3 py-2">
        <div class="d-flex">
          <img :src="require('@/assets/logo.png')" class="img-fluid align-self-start vh-10" 
          alt="Logo de IsFootball">
        </div>
        <p class="mb-0 fs-6">©{{ currentYear }} IsFootball. All rights reserved.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
  const currentYear = new Date().getFullYear();
  
  const infoLegalLinks= [
    { 
      title: 'Info',
      fields:[
        {
          label: 'About us', link: '/aboutUs'
        },
        {
          label: 'Contact', link: '/contact'
        },
        {
          label: 'FAQ', link: 'faq',
        },
        {
          label: 'Careers', link: '/careers'
        }
      ]
    },
    {
      title:'Legal',
      fields:[
        {
          label:'Privacy Policy', link: '/privacity',
        },
        {
            label: 'Terms of service', link:'/terms'
        },
        {
          label: 'Cookies',link:'/cookies'
        }
      ]
    }
  ];

  const followUsInfo={
    title:'Follow us',
    svgsInfo:
    [
      {
        link:'https://instagram.com',
        path:'M8,3c-2.757,0 -5,2.243 -5,5v8c0,2.757 2.243,5 5,5h8c2.757,0 5,-2.243 5,-5v-8c0,-2.757 -2.243,-5 -5,-5zM8,5h8c1.654,0 3,1.346 3,3v8c0,1.654 -1.346,3 -3,3h-8c-1.654,0 -3,-1.346 -3,-3v-8c0,-1.654 1.346,-3 3,-3zM17,6c-0.552,0 -1,0.448 -1,1s0.448,1 1,1 1,-0.448 1,-1 -0.448,-1 -1,-1zM12,7c-2.757,0 -5,2.243 -5,5s2.243,5 5,5 5,-2.243 5,-5 -2.243,-5 -5,-5zM12,9c1.654,0 3,1.346 3,3s-1.346,3 -3,3 -3,-1.346 -3,-3 1.346,-3 3,-3z'
      },
      {
        link:'https://youtube.com',
        path:'M21.582,6.186c-0.23,-0.86 -0.908,-1.538 -1.768,-1.768c-1.56,-0.418 -7.814,-0.418 -7.814,-0.418s-6.254,0 -7.814,0.418c-0.86,0.23 -1.538,0.908 -1.768,1.768c-0.418,1.56 -0.418,5.814 -0.418,5.814s0,4.254 0.418,5.814c0.23,0.86 0.908,1.538 1.768,1.768c1.56,0.418 7.814,0.418 7.814,0.418s6.254,0 7.814,-0.418c0.861,-0.23 1.538,-0.908 1.768,-1.768c0.418,-1.56 0.418,-5.814 0.418,-5.814s0,-4.254 -0.418,-5.814zM10,15.464v-6.928l6,3.464z'
      },
      {
        link:'https://facebook.com',
        path:'M11.666,2.005c-5.046,0.165 -9.292,4.246 -9.641,9.283c-0.369,5.329 3.442,9.832 8.481,10.589v-7.227h-1.614c-0.726,0 -1.314,-0.588 -1.314,-1.314s0.588,-1.314 1.314,-1.314h1.613v-1.749c0,-2.896 1.411,-4.167 3.818,-4.167c0.357,0 0.662,0.008 0.921,0.021c0.636,0.031 1.129,0.561 1.129,1.198c0,0.663 -0.537,1.2 -1.2,1.2h-0.442c-1.022,0 -1.379,0.969 -1.379,2.061v1.437h1.87c0.591,0 1.043,0.527 0.953,1.111l-0.108,0.701c-0.073,0.47 -0.477,0.817 -0.953,0.817h-1.762v7.247c4.883,-0.663 8.648,-4.837 8.648,-9.899c0,-5.634 -4.659,-10.179 -10.334,-9.995z'
      },
      {
        link:'https://x.com',
        path:'M6,3c-1.645,0 -3,1.355 -3,3v12c0,1.645 1.355,3 3,3h12c1.645,0 3,-1.355 3,-3v-12c0,-1.645 -1.355,-3 -3,-3zM6,5h12c0.565,0 1,0.435 1,1v12c0,0.565 -0.435,1 -1,1h-12c-0.565,0 -1,-0.435 -1,-1v-12c0,-0.565 0.435,-1 1,-1zM6.701,7l3.941,5.633l-3.736,4.367h1.332l3.002,-3.516l2.457,3.516h3.84l-4.186,-5.99l3.422,-4.01h-1.311l-2.703,3.16l-2.207,-3.16zM8.566,7.988h1.486l5.631,8.023h-1.496z'
      }
    ]
  };
</script> 