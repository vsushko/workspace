## Install the Hugging Face CLI

  The current Hugging Face command-line tool is called `hf`. The older `huggingface-cli` command is deprecated.

  ### macOS and Linux

  ```bash
  curl -LsSf https://hf.co/cli/install.sh | bash
  ```

  Restart your terminal. If `hf` is not found immediately, run:

  ```bash
  export PATH="$HOME/.local/bin:$PATH"
  ```

  ### Verify the installation

  ```bash
  hf version
  ```

  ### Authenticate with Hugging Face

  The model Bucket is private, so authenticate using an account that has access:

  ```bash
  hf auth login
  ```

  Follow the browser-based authorization instructions, then verify the active account:

  ```bash
  hf auth whoami
  ```

  > Never commit a Hugging Face access token to GitHub. The browser login stores the credential securely on the local machine.

  ## Download the model

  The complete model requires approximately 24 GB of storage. Keeping at least 30 GB free is recommended.

  On macOS or Linux:

  ```bash
  mkdir -p "$HOME/Models"

  hf buckets sync \
    hf://buckets/vasushko/Huihui-gemma-4-12B-coder-fable5-composer2.5-v1-abliterated-bucket \
    "$HOME/Models/Huihui-gemma-4-12B-coder-fable5-composer2.5-v1-abliterated"
  ```

  Running the same command again is safe. Hugging Face compares the local and remote files and transfers only missing or changed files.

  The downloaded model will be located at:

  ```text
  ~/Models/Huihui-gemma-4-12B-coder-fable5-composer2.5-v1-abliterated
  ```

  ## Updating the CLI

  ```bash
  hf update
  ```

  For additional information, see the official [Hugging Face CLI documentation](https://huggingface.co/docs/huggingface_hub/guides/cli) and [Buckets documentation](https://huggingface.co/
  docs/huggingface_hub/guides/buckets).
