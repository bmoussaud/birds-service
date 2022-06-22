SOURCE_IMAGE = os.getenv("SOURCE_IMAGE", default='harbor.mytanzu.xyz/library/micropet-tap-birds-sources')
LOCAL_PATH = os.getenv("LOCAL_PATH", default='.')
NAMESPACE = os.getenv("NAMESPACE", default='micropets-supplychain')

allow_k8s_contexts('aks-eu-tap-2')

k8s_custom_deploy(
    'birds',
    apply_cmd="/Users/benoitmoussaud/Downloads/xxxx/cli/distribution/darwin/amd64/cli/apps/v0.7.0/tanzu-apps-darwin_amd64 workload apply -f config/workload.yaml --live-update" +
               " --local-path " + LOCAL_PATH +
               " --source-image " + SOURCE_IMAGE +
               " --namespace " + NAMESPACE +
               " --yes >/dev/null" +
               " && kubectl get workload birds --namespace " + NAMESPACE + " -o yaml",
    delete_cmd="tanzu apps workload delete -f config/workload.yaml --namespace " + NAMESPACE + " --yes",
    deps=['pom.xml', './target/classes'],
    container_selector='workload',
    live_update=[
      sync('./target/classes', '/workspace/BOOT-INF/classes')
    ]
)

k8s_resource('birds', port_forwards=["8080:8080"],
            extra_pod_selectors=[{'serving.knative.dev/service': 'birds'}]) 