const smoothTransition = (event, url) => {
    event.preventDefault();
    document.body.classList.remove('loaded')
    document.body.classList.add('fadeOut');
    const form = event.target.closest('form');

    setTimeout(() => {
        if (form) {
            form.submit();
        } else {
            window.location.href = url;
        }
    }, 1000);
};

const links = document.querySelectorAll('a');
const forms = document.querySelectorAll('form');

links.forEach(link => {
    link.addEventListener('click', (event) => {
        smoothTransition(event, event.target.href);
    });
});

forms.forEach(form => {
    form.addEventListener('submit', (event) => {
        smoothTransition(event, form.action);
    });
});

window.addEventListener('load', () => {
    document.body.classList.remove('fadeOut')
    document.body.classList.add('loaded');
});
