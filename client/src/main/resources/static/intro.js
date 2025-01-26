document.addEventListener('DOMContentLoaded', function() {
    const h1 = document.querySelector('h1');
    const contentBox = document.querySelector('#content')

    contentBox.classList.add('disable')

    h1.addEventListener('animationend', function(){
        contentBox.style.filter = 'brightness(1)'
        contentBox.classList.remove('disable')
    })
});