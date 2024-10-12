<!-- Sidebar -->
<aside id="sidebar">
    <div class="sidebar-title">
        <div class="sidebar-brand">
            <span class="material-icons-outlined">inventory</span> Bob's Inventory
        </div>
        <span class="material-icons-outlined" onclick="closeSidebar()">close</span>
    </div>

    <ul class="sidebar-list">
        <li class="sidebar-list-item">
            <a href="${pageContext.request.contextPath}/" >
                <span class="material-icons-outlined">dashboard</span> Employees
            </a>
        </li>
        <li class="sidebar-list-item">
            <a href="${pageContext.request.contextPath}/offer">
                <span class="material-icons-outlined">inventory_2</span> offers
            </a>
        </li>
        <li class="sidebar-list-item">
            <a href="#">
                <span class="material-icons-outlined">fact_check</span> Inventory
            </a>
        </li>
        <li class="sidebar-list-item">
            <a href="#">
                <span class="material-icons-outlined">add_shopping_cart</span> Purchase Orders
            </a>
        </li>
        <li class="sidebar-list-item">
            <a href="#">
                <span class="material-icons-outlined">shopping_cart</span> Sales Orders
            </a>
        </li>
        <li class="sidebar-list-item">
            <a href="#">
                <span class="material-icons-outlined">poll</span> Reports
            </a>
        </li>
        <li class="sidebar-list-item">
            <a href="#">
                <span class="material-icons-outlined">settings</span> Settings
            </a>
        </li>
    </ul>
</aside>
<!-- End Sidebar -->