 <script>
        const dropArea = document.getElementById('drop-area');
        const fileInput = document.getElementById('fileInput');

        dropArea.addEventListener('dragover', (e) => {
            e.preventDefault();
            dropArea.classList.add('dragover');
        });

        dropArea.addEventListener('dragleave', () => {
            dropArea.classList.remove('dragover');
        });

        dropArea.addEventListener('drop', (e) => {
            e.preventDefault();
            dropArea.classList.remove('dragover');
            const files = e.dataTransfer.files;
            handleFiles(files);
        });

        function handleFiles(files) {
            if (files.length > 0) {
                fileInput.files = files;
                alert(`${files[0].name} が選択されました`);
            }
        }
    </script>