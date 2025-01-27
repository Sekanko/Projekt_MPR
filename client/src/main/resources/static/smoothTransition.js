const smoothTransition = (event, url) => {
    if (event.target.id === 'download'){
        return;
    }
    event.preventDefault();

    document.body.classList.remove('loaded');
    document.body.classList.add('fadeOut');

    const form = event.target.closest('form');

    setTimeout(() => {
        if (form) {
            const formData = new FormData(form);
            const actionUrl = form.getAttribute('action');

            if (form.getAttribute('method').toUpperCase() === "GET"){
                form.submit();
            } else {
                fetch(actionUrl, {
                    method: 'POST',
                    body: formData
                })
                    .then(response => {
                        if (response.ok) {
                            window.location.href = response.url;
                        } else {
                            console.error('Server error:', response.status);
                            document.body.classList.remove('fadeOut');
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        document.body.classList.remove('fadeOut');
                    });
            }

        } else {
            window.location.href = url;
        }
    }, 1000);
};

const apear = () => {
    document.body.classList.remove('fadeOut')
    document.body.classList.add('loaded');
}

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
    apear()
});